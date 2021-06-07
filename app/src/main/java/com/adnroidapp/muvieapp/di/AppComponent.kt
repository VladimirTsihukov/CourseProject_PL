package com.adnroidapp.muvieapp.di

import com.adnroidapp.muvieapp.di.modules.ApiModule
import com.adnroidapp.muvieapp.di.modules.AppModule
import com.adnroidapp.muvieapp.di.modules.CiceroneModule
import com.adnroidapp.muvieapp.di.modules.DatabaseModule
import com.adnroidapp.muvieapp.di.movie.MovieSubComponent
import com.adnroidapp.muvieapp.presenter.PresenterMain
import com.adnroidapp.muvieapp.ui.ActivityMain
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class]
)
interface AppComponent {

    fun movieSubComponent(): MovieSubComponent

    fun inject(mainActivity: ActivityMain)
    fun inject(presenterMain: PresenterMain)
}