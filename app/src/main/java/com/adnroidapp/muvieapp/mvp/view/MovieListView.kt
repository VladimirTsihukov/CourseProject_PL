package com.adnroidapp.muvieapp.mvp.view

import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MovieListView: MvpView {
    fun initAdapter()
    fun updateList(newMovies: List<Movie>)
    fun release()
}