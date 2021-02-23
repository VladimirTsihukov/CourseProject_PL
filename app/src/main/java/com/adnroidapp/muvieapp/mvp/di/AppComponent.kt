package com.adnroidapp.muvieapp.mvp.di

import com.adnroidapp.muvieapp.mvp.di.modules.*
import com.adnroidapp.muvieapp.mvp.di.movie.MovieSubComponent
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
        DatabaseModule::class]
)
interface AppComponent {

    fun movieSubComponent(): MovieSubComponent

    fun inject(mainActivity: MainActivity)
    fun inject(presenterMain: PresenterMain)
}