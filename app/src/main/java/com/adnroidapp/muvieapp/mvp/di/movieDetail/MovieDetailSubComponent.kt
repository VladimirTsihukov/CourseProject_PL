package com.adnroidapp.muvieapp.mvp.di.movieDetail

import com.adnroidapp.muvieapp.mvp.di.movieDetail.module.MovieDetailModule
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieDetail
import com.adnroidapp.muvieapp.ui.adapter.AdapterActors
import dagger.Subcomponent

@MovieDetailScope
@Subcomponent(modules = [MovieDetailModule::class])
interface MovieDetailSubComponent {
    fun inject(presenterMovieDetail: PresenterMovieDetail)
    fun inject(adapterActors: AdapterActors)
}