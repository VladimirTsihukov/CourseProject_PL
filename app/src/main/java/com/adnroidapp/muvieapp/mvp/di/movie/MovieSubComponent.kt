package com.adnroidapp.muvieapp.mvp.di.movie

import com.adnroidapp.muvieapp.mvp.di.movie.module.MovieModule
import com.adnroidapp.muvieapp.mvp.di.movieDetail.MovieDetailSubComponent
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieList
import com.adnroidapp.muvieapp.ui.adapter.AdapterMoviesFilm
import dagger.Subcomponent

@MovieScope
@Subcomponent(modules = [MovieModule::class])
interface MovieSubComponent {
    fun movieDetailSubComponent(): MovieDetailSubComponent

    fun inject(presenterMovieList: PresenterMovieList)
    fun inject(adapterMoviesFilm: AdapterMoviesFilm)
}