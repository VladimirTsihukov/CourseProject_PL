package com.adnroidapp.muvieapp.mvp.model.cache

import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IMoviesCache {
    fun getCacheMoviesCategory(moviePopular: Boolean): Single<List<Movie>>
    fun putCacheMovies(movies: List<Movie>, moviesPopular: Boolean)
    fun deleteMoviesCategory(moviePopular: Boolean): Completable

    fun getCacheMoviesLike(): Single<List<Movie>>
    fun putCacheMoviesLike(movies: Movie) : Single<Boolean>
    fun getMovieLikeID(): List<Long>
    fun deleteMovieLike(id : Long) : Single<Boolean>
}