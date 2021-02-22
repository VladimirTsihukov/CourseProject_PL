package com.adnroidapp.muvieapp.mvp.di.modules

import androidx.room.Room
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesDetailCache
import com.adnroidapp.muvieapp.mvp.model.cache.roomcache.CacheRoomMovieDetail
import com.adnroidapp.muvieapp.mvp.model.cache.roomcache.CacheRoomMovies
import com.adnroidapp.muvieapp.mvp.model.entity.room.DatabaseContact
import com.adnroidapp.muvieapp.mvp.model.entity.room.db.DBMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(app: App): DBMovies = Room.databaseBuilder(
        app,
        DBMovies::class.java,
        DatabaseContact.DATABASE_NAME_MOVIES
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun movieCache(db: DBMovies): IMoviesCache = CacheRoomMovies(db)

    @Singleton
    @Provides
    fun movieDetailCache(db: DBMovies): IMoviesDetailCache = CacheRoomMovieDetail(db)

}