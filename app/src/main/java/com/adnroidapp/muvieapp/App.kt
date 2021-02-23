package com.adnroidapp.muvieapp

import android.app.Application
import android.util.Log
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import com.adnroidapp.muvieapp.mvp.di.AppComponent
import com.adnroidapp.muvieapp.mvp.di.DaggerAppComponent
import com.adnroidapp.muvieapp.mvp.di.modules.AppModule
import com.adnroidapp.muvieapp.mvp.di.movie.MovieSubComponent
import com.adnroidapp.muvieapp.mvp.di.movieDetail.MovieDetailSubComponent

class App : Application() {

    lateinit var appComponent: AppComponent
    private set

    var movieSubComponent: MovieSubComponent? = null
    private set

    var movieDetailSubComponent: MovieDetailSubComponent? = null
    private set

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(LOG_KEY, "Create App")
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initMovieSubComponent() = appComponent.movieSubComponent().also {
        movieSubComponent = it
    }

    fun releaseMovieSubComponent() {
        movieSubComponent = null
    }

    fun initMovieDetailSubComponent() = movieSubComponent?.movieDetailSubComponent().also {
        movieDetailSubComponent = it
    }

    fun releaseMovieDetailSubComponent() {
        movieDetailSubComponent = null
    }
}