package com.adnroidapp.muvieapp.model.entity.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adnroidapp.muvieapp.model.entity.dao.DaoMovies
import com.adnroidapp.muvieapp.model.entity.dao.DaoMoviesDetail
import com.adnroidapp.muvieapp.model.entity.dao.DaoMoviesLike
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import com.adnroidapp.muvieapp.model.entity.room.data.RoomLikeMovie
import com.adnroidapp.muvieapp.model.entity.room.data.RoomMovie

@Database(
    entities = [RoomDetailMovie::class,
        RoomLikeMovie::class,
        RoomMovie::class], version = 1
)
abstract class DBMovies : RoomDatabase() {

    abstract fun movies(): DaoMovies
    abstract fun moviesLike(): DaoMoviesLike
    abstract fun moviesDetail(): DaoMoviesDetail
}