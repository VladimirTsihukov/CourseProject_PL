package com.adnroidapp.muvieapp

import android.app.Application
import android.util.Log
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router


class App : Application() {

    private val cicerone: Cicerone<Router> by lazy {
        Log.v(LOG_KEY, "Create Cicerone")
        Cicerone.create()
    }

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(LOG_KEY, "Create App")
        instance = this
    }

    val navigatorHolder
        get() = cicerone.navigatorHolder

    val router
        get() = cicerone.router
}