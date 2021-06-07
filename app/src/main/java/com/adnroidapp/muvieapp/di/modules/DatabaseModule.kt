package com.adnroidapp.muvieapp.di.modules

import androidx.room.Room
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.model.entity.room.DatabaseContact
import com.adnroidapp.muvieapp.model.entity.room.db.DBMovies
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
}