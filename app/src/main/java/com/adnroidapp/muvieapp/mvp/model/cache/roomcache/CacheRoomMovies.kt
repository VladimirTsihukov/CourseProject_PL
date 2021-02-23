package com.adnroidapp.muvieapp.mvp.model.cache.roomcache

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomLikeMovie
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomMovie
import com.adnroidapp.muvieapp.mvp.model.entity.room.db.DBMovies
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

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
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount,
                    adult = it.adult,
                    likeMovies = it.likeMovies
                )
            }
        }

    override fun putCacheMovies(movies: List<Movie>, moviesPopular: Boolean) {
        Log.v(ClassKey.LOG_KEY, "CacheRoomMovies putCacheMovies(movies.size = ${movies.size})")
        val listMovies = movies.map {
            RoomMovie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                adult = it.adult,
                likeMovies = it.likeMovies,
                moviePopular = moviesPopular,
            )
        }
        db.movies().insertMovies(listMovies)
    }

    override fun deleteMoviesCategory(moviePopular: Boolean): Completable =
        Completable.fromCallable {
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
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                adult = it.adult,
                likeMovies = it.likeMovies
            )
        }
    }.subscribeOn(Schedulers.io())

    override fun putCacheMoviesLike(movies: Movie): Single<Boolean>  = Single.fromCallable {
        db.moviesLike().insetMoviesLike(movies.let {
            RoomLikeMovie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                adult = it.adult,
                likeMovies = it.likeMovies
            )
        })
        Log.v(ClassKey.LOG_KEY, "CacheRoomMovies putCacheMoviesLike()")
        true
    }.subscribeOn(Schedulers.io())

    override fun getMovieLikeID(): List<Long> = db.moviesLike().getAllID()


    override fun deleteMovieLike(id: Long): Single<Boolean> = Single.fromCallable {
        db.moviesLike().deleteMoviesLike(id)
        true
    }.subscribeOn(Schedulers.io())
}