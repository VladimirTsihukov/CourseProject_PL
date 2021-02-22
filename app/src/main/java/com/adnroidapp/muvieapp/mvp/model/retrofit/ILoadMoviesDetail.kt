package com.adnroidapp.muvieapp.mvp.model.retrofit

import com.adnroidapp.muvieapp.mvp.model.api.data.Cast
import com.adnroidapp.muvieapp.mvp.model.api.data.MovieActors
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomDetailMovie
import io.reactivex.rxjava3.core.Single

interface ILoadMoviesDetail {
    fun retrofitLoadMoviesDetail(id: Long): Single<RoomDetailMovie>
    fun retrofitLoadMovieActors(id: Long): Single<List<Cast>>
}