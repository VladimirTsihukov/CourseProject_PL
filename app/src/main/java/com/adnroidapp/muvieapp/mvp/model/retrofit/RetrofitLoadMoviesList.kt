package com.adnroidapp.muvieapp.mvp.model.retrofit

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import com.adnroidapp.muvieapp.mvp.model.api.ApiService
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitLoadMoviesList(
    private val api: ApiService,
    private val networkStatus: INetworkStatus,
    private val cache: IMoviesCache
) : ILoadMoviesList {

    override fun retrofitLoadMoviesPopular(): Single<List<Movie>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            Log.v(LOG_KEY, "RetrofitLoadMoviesPopular retrofitLoadMoviesPopular()")
            if (isOnline) {
                api.getMoviePopular().map {movieList ->
                    Log.v(LOG_KEY, "RetrofitLoadMoviesPopular list.size = ${movieList.results.size}")
                    cache.deleteMoviesCategory(true)
                    val listIdMovieLike = cache.getMovieLikeID()
                    movieList.results.forEach {
                        if (listIdMovieLike.contains(it.id)) {
                            it.likeMovies = true
                        }
                    }
                    cache.putCacheMovies(movieList.results, true)
                    movieList.results
                }
            } else {
                cache.getCacheMoviesCategory(true)
            }
        }.subscribeOn(Schedulers.io())

    override fun retrofitLoadMoviesTopRate(): Single<List<Movie>> =
        networkStatus.isOnlineSingle().flatMap { online ->
            Log.v(LOG_KEY, "RetrofitLoadMoviesPopular retrofitLoadMoviesTopRate()")
            if (online) {
                api.getMoviesTopRate().map { movieList ->
                    Log.v(LOG_KEY, "RetrofitLoadTopRate list.size = ${movieList.results.size}")
                    cache.deleteMoviesCategory(false)
                    val listIdMovieLike = cache.getMovieLikeID()
                    movieList.results.forEach {
                        if (listIdMovieLike.contains(it.id)) {
                            it.likeMovies = true
                        }
                    }
                    cache.putCacheMovies(movieList.results, false)
                    movieList.results
                }
            } else {
                cache.getCacheMoviesCategory(false)
            }
        }.subscribeOn(Schedulers.io())
}