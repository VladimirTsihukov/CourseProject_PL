package com.adnroidapp.muvieapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.model.api.ApiFactory
import com.adnroidapp.muvieapp.mvp.model.api.ApiFactory.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.muvieapp.mvp.model.api.data.Cast
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.mvp.model.retrofit.RetrofitLoadMoviesList
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieDetail
import com.adnroidapp.muvieapp.mvp.view.MovieDetailView
import com.adnroidapp.muvieapp.ui.adapter.AdapterActors
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActor
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderMovies
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_details.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import kotlin.math.roundToInt

class FragmentMovieDetail : MvpAppCompatFragment(R.layout.fragment_movie_details), MovieDetailView {

    private lateinit var imagePoster: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private lateinit var listStar: List<ImageView>

    private val loaderGlideMovie by lazy {
        GlideImageLoaderMovies()
    }

    private val presenter: PresenterMovieDetail by moxyPresenter {
        Log.v(LOG_KEY, "FragmentMovieDetail: init presenter")
        val movieID = arguments?.getLong(KEY_ID_MOVIE_DETAIL) ?: -1L

        PresenterMovieDetail(
            movieId = movieID,
            mainThreadScheduler = AndroidSchedulers.mainThread(),
            retrofitLoadMovies = RetrofitLoadMoviesList(ApiFactory.apiServiceMovies)
        )
    }

    private val recyclerView: RecyclerView? by lazy {
        view?.findViewById(R.id.rec_actors)
    }
    private lateinit var adapter: AdapterActors

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(LOG_KEY, "FragmentMovieDetail: onViewCreated()")
        activity?.bottom_navigation?.visibility = View.GONE
        initView(view)
    }

    private fun initView(view: View) {
        Log.v(LOG_KEY, "FragmentMovieDetail: initView()")

        imagePoster = view.findViewById(R.id.mask)
        star1 = view.findViewById(R.id.mov_list_star_level_1)
        star2 = view.findViewById(R.id.mov_list_star_level_2)
        star3 = view.findViewById(R.id.mov_list_star_level_3)
        star4 = view.findViewById(R.id.mov_list_star_level_4)
        star5 = view.findViewById(R.id.mov_list_star_level_5)
        listStar = listOf(star1, star2, star3, star4, star5)
    }

    companion object {
        private const val KEY_ID_MOVIE_DETAIL = "KEY_ID_MOVIE"
        fun newInstance(id: Long) = FragmentMovieDetail().apply {
            Log.v(LOG_KEY, "FragmentMovieDetail: newInstance(id = $id)")
            arguments = Bundle().apply {
                putLong(KEY_ID_MOVIE_DETAIL, id)
            }
        }
    }

    override fun initAdapterActor() {
        adapter = AdapterActors(GlideImageLoaderActor())
        recyclerView?.adapter = adapter
    }

    override fun initViewMovieDetail(movieDetail: MoviesDetail) {
        view?.let {
            setPosterIcon(BASE_URL_MOVIE_IMAGE + movieDetail.backdropPath)
            mov_list_age_category.text = resources.getString(R.string.fragment_age_category).let {
                String.format(it, "${if (movieDetail.adult) 16 else 13}")
            }
            mov_list_movie_genre.text = movieDetail.genres.joinToString(", ") { it.name }
            mov_list_text_story_line.text = movieDetail.overview
            mov_list_reviews.text = resources.getString(R.string.fragment_reviews).let {
                String.format(it, "${movieDetail.voteCount}")
            }
            mov_list_film_name.text = movieDetail.title
        }
        setImageStars((movieDetail.voteAverage / 2).roundToInt())
    }

    override fun updateAdapterActor(listActor: List<Cast>) {
        adapter.bindActors(listActor)
    }

    override fun invisibleLoader() {
        view?.let {
            dataLoader.visibility = View.INVISIBLE
        }
    }

    private fun setPosterIcon(poster: String) {
        loaderGlideMovie.loadInto(poster, imagePoster)
    }

    private fun setImageStars(current: Int) {

        listStar.forEachIndexed { index, _ ->
            if (index < current) {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.star_icon_on)
            } else {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.star_icon_off)
            }
        }
    }
}