package com.adnroidapp.muvieapp.mvp.model.retrofit

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.api.ApiService
import com.adnroidapp.muvieapp.mvp.model.api.data.Cast
import com.adnroidapp.muvieapp.mvp.model.api.data.getMovieRoom
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesDetailCache
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomDetailMovie
import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class RetrofitLoadMovieDetail(
    private val api: ApiService,
    private val networkStatus: INetworkStatus,
    private val cache: IMoviesDetailCache
) : ILoadMoviesDetail {

    override fun retrofitLoadMoviesDetail(id: Long): Single<RoomDetailMovie> =
        networkStatus.isOnlineSingle().flatMap { online ->
            if (online) {
                api.getMovieDetails(id).map { movieDetail ->
                    cache.putMoviesDetail(movieDetail)
                    movieDetail.getMovieRoom()
                }
            } else {
                cache.getMoviesDetailForID(id)
            }
        }.subscribeOn(Schedulers.io())

    override fun retrofitLoadMovieActors(id: Long): Single<List<Cast>> =
        networkStatus.isOnlineSingle().flatMap { online ->
            if (online) {
                api.getMovieActors(id).map {
                    Log.v(ClassKey.LOG_KEY, "Load retrofitLoadMovieActors actor.size = ${it.cast.size}")
                    cache.putMoviesActors(id, it.cast)
                    it.cast
                }
            } else {
                cache.getMovieActors(id)
            }
        }.subscribeOn(Schedulers.io())

}