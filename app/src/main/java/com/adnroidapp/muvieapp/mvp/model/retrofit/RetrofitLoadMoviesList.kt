package com.adnroidapp.muvieapp.mvp.model.retrofit

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.api.ApiService
import com.adnroidapp.muvieapp.mvp.model.api.data.MovieActors
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesList
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitLoadMoviesList(private val api: ApiService) : ILoadMoviesList {

    override fun retrofitLoadMoviesPopular(): Single<MoviesList> =
        api.getMoviePopular().map {
            Log.v(ClassKey.LOG_KEY, "RetrofitLoadMoviesPopular list.size = ${it.results.size}")
            it
        }.subscribeOn(Schedulers.io())

    override fun retrofitLoadMoviesTopRate(): Single<MoviesList> =
        api.getMoviesTopRate().map {
            Log.v(ClassKey.LOG_KEY, "RetrofitLoadTopRate list.size = ${it.results.size}")
            it
        }.subscribeOn(Schedulers.io())

    override fun retrofitLoadMoviesDetail(id: Long): Single<MoviesDetail> =
        api.getMovieDetails(id).map {
            Log.v(ClassKey.LOG_KEY, "Load retrofitLoadMoviesDetail movie name = ${it.title}")
            it
        }.subscribeOn(Schedulers.io())


    override fun retrofitLoadMovieActors(id: Long): Single<MovieActors> =
        api.getMovieActors(id).map {
            Log.v(ClassKey.LOG_KEY, "Load retrofitLoadMovieActors actor.size = ${it.cast.size}")
            it
        }.subscribeOn(Schedulers.io())
}