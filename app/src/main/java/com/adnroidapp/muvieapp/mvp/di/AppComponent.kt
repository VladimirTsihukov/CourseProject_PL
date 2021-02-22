package com.adnroidapp.muvieapp.mvp.di

import com.adnroidapp.muvieapp.mvp.di.modules.AppModule
import com.adnroidapp.muvieapp.mvp.di.modules.CiceroneModule
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMain
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieDetail
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieList
import com.adnroidapp.muvieapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        AppModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(presenterMain: PresenterMain)
    fun inject(presenterMovieList: PresenterMovieList)
    fun inject(presenterMovieDetail: PresenterMovieDetail)
}