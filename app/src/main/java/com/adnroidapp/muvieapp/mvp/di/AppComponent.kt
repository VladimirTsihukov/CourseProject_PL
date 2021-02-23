package com.adnroidapp.muvieapp.mvp.di

import com.adnroidapp.muvieapp.mvp.di.modules.*
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMain
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieDetail
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieList
import com.adnroidapp.muvieapp.ui.MainActivity
import com.adnroidapp.muvieapp.ui.adapter.AdapterActors
import com.adnroidapp.muvieapp.ui.adapter.AdapterMoviesFilm
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class,
        RepoModule::class,
        ImageLoadModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(presenterMain: PresenterMain)
    fun inject(presenterMovieList: PresenterMovieList)
    fun inject(presenterMovieDetail: PresenterMovieDetail)
    fun inject(adapterActors: AdapterActors)
    fun inject(adapterMoviesFilm: AdapterMoviesFilm)
}