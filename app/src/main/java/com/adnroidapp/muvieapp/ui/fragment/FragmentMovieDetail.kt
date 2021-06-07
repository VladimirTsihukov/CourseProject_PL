package com.adnroidapp.muvieapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.di.movieDetail.MovieDetailSubComponent
import com.adnroidapp.muvieapp.model.api.data.Cast
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import com.adnroidapp.muvieapp.presenter.PresenterMovieDetail
import com.adnroidapp.muvieapp.presenter.view.ViewMovieDetail
import com.adnroidapp.muvieapp.ui.BackButtonListener
import com.adnroidapp.muvieapp.ui.adapter.AdapterActors
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorMovies
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_back_fragment.view.*
import kotlinx.android.synthetic.main.layout_stars.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class FragmentMovieDetail : MvpAppCompatFragment(R.layout.fragment_movie_details),
    ViewMovieDetail, BackButtonListener {

    private var movieDetailSubComponent: MovieDetailSubComponent? = null

    private val loaderGlideMovie by lazy {
        GlideImageLoaderActorMovies()
    }

    private val presenter: PresenterMovieDetail by moxyPresenter {
        movieDetailSubComponent = App.instance.initMovieDetailSubComponent()
        val movieID = arguments?.getLong(KEY_ID_MOVIE_DETAIL) ?: -1L
        PresenterMovieDetail(movieId = movieID).apply {
            movieDetailSubComponent?.inject(this)
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterActors

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailSubComponent = App.instance.initMovieDetailSubComponent()

        initView(view)
        initAdapterActor()
    }

    private fun initView(view: View) {
        activity?.bottom_navigation?.visibility = View.GONE
        recyclerView = view.findViewById(R.id.rec_view_actors)

        view.tv_back.setOnClickListener {
            presenter.backPressed()
        }
    }

    private fun initAdapterActor() {
        adapter = AdapterActors().apply { movieDetailSubComponent?.inject(this) }
        recyclerView.adapter = adapter
    }

    override fun initViewMovieDetail(movieDetail: RoomDetailMovie) {
        view?.also {
            tv_mov_list_age_category.text =
                resources.getString(R.string.fragment_age_category).let {
                    String.format(it, "${movieDetail.minimumAge}")
                }
            tv_mov_list_movie_genre.text = movieDetail.genres
            tv_mov_list_text_story_line.text = movieDetail.overview
            tv_mov_list_reviews.text = resources.getString(R.string.fragment_reviews).let {
                String.format(it, "${movieDetail.numberOfRatings}")
            }
            tv_mov_list_film_name.text = movieDetail.title
            setPosterIcon(movieDetail.backdrop)
            setImageStars((movieDetail.ratings / 2).roundToInt())
        }
    }

    override fun updateAdapterActor(listActor: List<Cast>) {
        adapter.bindActors(listActor)
    }

    @SuppressLint("CheckResult")
    override fun invisibleLoader() {
        Observable.timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view?.let {
                    data_loader.visibility = View.INVISIBLE
                }
            }
    }

    override fun release() {
        App.instance.releaseMovieDetailSubComponent()
        movieDetailSubComponent = null
    }

    private fun setPosterIcon(poster: String) {
        loaderGlideMovie.loadInto(poster, img_poster)
    }

    private fun setImageStars(current: Int) {
        view?.apply {
            val listStar = listOf<ImageView>(
                img_mov_list_star_level_1,
                img_mov_list_star_level_2,
                img_mov_list_star_level_3,
                img_mov_list_star_level_4,
                img_mov_list_star_level_5
            )

            listStar.forEachIndexed { index, _ ->
                if (index < current) {
                    listStar[index].setImageResource(R.drawable.ic_star_on)
                } else {
                    listStar[index].setImageResource(R.drawable.ic_star_off)
                }
            }
        }
    }

    override fun backPressed(): Boolean {
        return presenter.backPressed()
    }

    companion object {
        private const val KEY_ID_MOVIE_DETAIL = "KEY_ID_MOVIE"
        fun newInstance(id: Long) = FragmentMovieDetail().apply {
            arguments = Bundle().apply {
                putLong(KEY_ID_MOVIE_DETAIL, id)
            }
        }
    }
}