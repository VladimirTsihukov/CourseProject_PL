package com.adnroidapp.muvieapp.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.ClassKey.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoader
import com.adnroidapp.muvieapp.mvp.view.preenterView.PresenterDetailViewClick
import kotlin.math.roundToInt

class AdapterMoviesFilm(
    private val presenter: PresenterDetailViewClick,
    private val imageLoaderMovie: IImageLoader<ImageView>
) : RecyclerView.Adapter<AdapterMoviesFilm.HolderMovies>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovies {
        return HolderMovies(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderMovies, position: Int) {
        holder.onBind(movies[position])
        holder.item.setOnClickListener {
            Log.v(LOG_KEY, "AdapterMoviesFilm: click movie id = ${movies[position].id}")
            presenter.clickMovie(movies[position].id) }
        holder.iconLike.setOnClickListener {
            Log.v(LOG_KEY, "AdapterMoviesFilm: click like icon")
            presenter.clickLikeIcon(movies[position].likeMovies, movies[position])
            movies[position].likeMovies = !movies[position].likeMovies
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

   inner class HolderMovies(val item: View) : RecyclerView.ViewHolder(item) {
        private val imageFilm: ImageView = item.findViewById(R.id.holder_image_film)
        private val ageCategory: TextView = item.findViewById(R.id.holder_age_category)
        private val star1: ImageView = item.findViewById(R.id.holder_star_level_1)
        private val star2: ImageView = item.findViewById(R.id.holder_star_level_2)
        private val star3: ImageView = item.findViewById(R.id.holder_star_level_3)
        private val star4: ImageView = item.findViewById(R.id.holder_star_level_4)
        private val star5: ImageView = item.findViewById(R.id.holder_star_level_5)
        private val listStar: List<ImageView> = listOfNotNull(star1, star2, star3, star4, star5)
        private val reviews: TextView = item.findViewById(R.id.holder_reviews)
        private val filName: TextView = item.findViewById(R.id.holder_film_name)
        val iconLike: ImageView = item.findViewById(R.id.holder_icon_like)

        @SuppressLint("SetTextI18n")
        fun onBind(movie: Movie) {

            setPosterIcon(BASE_URL_MOVIE_IMAGE + movie.posterPath)
            ageCategory.text = item.context.resources.getString(R.string.fragment_age_category).let {
                String.format(it, "${if (movie.adult) 16 else 13}")}
            setImageStars((movie.voteAverage / 2).roundToInt())
            reviews.text = item.context.resources.getString(R.string.fragment_reviews).let {
                String.format(it, "${movie.voteCount}")
            }
            filName.text = movie.title
            iconLike.setImageResource(
                if (movie.likeMovies) {
                    R.drawable.icon_like_off
                } else {
                    R.drawable.icon_like_on
                }
            )
        }

        private fun setPosterIcon(poster: String) {
            imageLoaderMovie.loadInto(poster, imageFilm)
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
}
