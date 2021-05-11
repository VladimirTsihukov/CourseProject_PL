package com.adnroidapp.muvieapp.presenter.view

import com.adnroidapp.muvieapp.model.api.data.Movie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewMovieList: MvpView {
    fun initAdapter()
    fun updateList(newMovies: List<Movie>)
    fun release()
}