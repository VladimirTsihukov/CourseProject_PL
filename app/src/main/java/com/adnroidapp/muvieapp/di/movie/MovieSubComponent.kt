package com.adnroidapp.muvieapp.di.movie

import com.adnroidapp.muvieapp.di.movie.module.MovieModule
import com.adnroidapp.muvieapp.di.movieDetail.MovieDetailSubComponent
import com.adnroidapp.muvieapp.presenter.PresenterMovieListPresenterDetail
import com.adnroidapp.muvieapp.ui.adapter.AdapterMoviesFilm
import dagger.Subcomponent

@MovieScope
@Subcomponent(modules = [MovieModule::class])
interface MovieSubComponent {
    fun movieDetailSubComponent(): MovieDetailSubComponent

    fun inject(presenterMovieList: PresenterMovieListPresenterDetail)
    fun inject(adapterMoviesFilm: AdapterMoviesFilm)
}