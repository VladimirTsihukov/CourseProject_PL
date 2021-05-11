package com.adnroidapp.muvieapp.model.cache

import com.adnroidapp.muvieapp.model.api.data.Cast
import com.adnroidapp.muvieapp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import io.reactivex.Single

interface IMoviesDetailCache {

    fun getMoviesDetailForID(id: Long): Single<RoomDetailMovie>
    fun putMoviesDetail(movies: MoviesDetail)

    fun getMovieActors(id: Long): Single<List<Cast>>
    fun putMoviesActors(id: Long, listCast: List<Cast>)
}