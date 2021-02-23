package com.adnroidapp.muvieapp.mvp.model.cache

import com.adnroidapp.muvieapp.mvp.model.api.data.Cast
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomDetailMovie
import io.reactivex.rxjava3.core.Single

interface IMoviesDetailCache {

    fun getMoviesDetailForID(id: Long): Single<RoomDetailMovie>
    fun putMoviesDetail(movies: MoviesDetail)

    fun getMovieActors(id: Long): Single<List<Cast>>
    fun putMoviesActors(id: Long, listCast: List<Cast>)
}