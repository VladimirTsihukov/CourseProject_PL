package com.adnroidapp.muvieapp.model.cache.roomcache

import com.adnroidapp.muvieapp.model.api.data.Movie
import com.adnroidapp.muvieapp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.model.entity.room.data.RoomLikeMovie
import com.adnroidapp.muvieapp.model.entity.room.data.RoomMovie
import com.adnroidapp.muvieapp.model.entity.room.db.DBMovies
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CacheRoomMovies(private val db: DBMovies) : IMoviesCache {

    override fun getCacheMoviesCategory(moviePopular: Boolean): Single<List<Movie>> =
        Single.fromCallable {
            db.movies().getMoviesCategory(moviePopular).map {
                Movie(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
                    ratings = it.voteAverage,
                    voteCount = it.voteCount,
                    adult = it.adult,
                    likeMovies = it.likeMovies
                )
            }
        }

    override fun putCacheMovies(movies: List<Movie>, moviesPopular: Boolean) {
        val listMovies = movies.map {
            RoomMovie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.ratings,
                voteCount = it.voteCount,
                adult = it.adult,
                likeMovies = it.likeMovies,
                moviePopular = moviesPopular,
            )
        }
        db.movies().insertMovies(listMovies)
    }

    override fun deleteMoviesCategory(moviePopular: Boolean) {
        db.movies().deleteMoviesCategory(moviePopular)
    }

    override fun getCacheMoviesLike(): Single<List<Movie>> = Single.fromCallable {
        db.moviesLike().getMoviesLike().map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                ratings = it.voteAverage,
                voteCount = it.voteCount,
                adult = it.adult,
                likeMovies = it.likeMovies
            )
        }
    }.subscribeOn(Schedulers.io())

    override fun putCacheMoviesLike(movies: Movie): Completable = Completable.fromCallable {
        db.moviesLike().insetMoviesLike(movies.let {
            RoomLikeMovie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.ratings,
                voteCount = it.voteCount,
                adult = it.adult,
                likeMovies = it.likeMovies
            )
        })
    }.subscribeOn(Schedulers.io())

    override fun getMovieLikeID(): List<Long> = db.moviesLike().getAllID()


    override fun deleteMovieLike(id: Long): Completable = Completable.fromCallable {
        db.moviesLike().deleteMoviesLike(id)
    }.subscribeOn(Schedulers.io())
}