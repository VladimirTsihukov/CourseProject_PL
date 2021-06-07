package com.adnroidapp.muvieapp.model.retrofit

import com.adnroidapp.muvieapp.model.api.data.Cast
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import io.reactivex.Single

interface ILoadMoviesDetail {
    fun retrofitLoadMoviesDetail(id: Long): Single<RoomDetailMovie>
    fun retrofitLoadMovieActors(id: Long): Single<List<Cast>>
}