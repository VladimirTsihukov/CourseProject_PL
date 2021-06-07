package com.adnroidapp.muvieapp.model.retrofit

import com.adnroidapp.muvieapp.model.api.ApiService
import com.adnroidapp.muvieapp.model.api.data.Cast
import com.adnroidapp.muvieapp.model.api.data.getMovieRoom
import com.adnroidapp.muvieapp.model.cache.IMoviesDetailCache
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import com.adnroidapp.muvieapp.model.newtwork.INetworkStatus
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
                    cache.putMoviesActors(id, it.cast)
                    it.cast
                }
            } else {
                cache.getMovieActors(id)
            }
        }.subscribeOn(Schedulers.io())

}