package com.adnroidapp.muvieapp

import android.app.Application
import android.util.Log
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import com.adnroidapp.muvieapp.mvp.di.AppComponent
import com.adnroidapp.muvieapp.mvp.di.DaggerAppComponent
import com.adnroidapp.muvieapp.mvp.di.modules.AppModule

class App : Application() {

    lateinit var appComponent: AppComponent

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
}