package com.adnroidapp.muvieapp.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase

import com.adnroidapp.muvieapp.mvp.model.entity.dao.DaoMovies
import com.adnroidapp.muvieapp.mvp.model.entity.dao.DaoMoviesDetail
import com.adnroidapp.muvieapp.mvp.model.entity.dao.DaoMoviesLike
import com.adnroidapp.muvieapp.mvp.model.entity.room.DatabaseContact
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomDetailMovie
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomLikeMovie
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomMovie

@Database(
    entities = [RoomDetailMovie::class,
        RoomLikeMovie::class,
        RoomMovie::class], version = 1
)
abstract class DBMovies : RoomDatabase() {

    abstract fun movies(): DaoMovies
    abstract fun moviesLike(): DaoMoviesLike
    abstract fun moviesDetail(): DaoMoviesDetail

    companion object {
        private var db: DBMovies? = null
        private val LOCK = Any()

        fun instance(context: Context): DBMovies {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    DBMovies::class.java,
                    DatabaseContact.DATABASE_NAME_MOVIES
                ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }
}