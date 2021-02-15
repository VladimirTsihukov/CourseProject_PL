package com.adnroidapp.muvieapp.mvp.view

import com.adnroidapp.muvieapp.mvp.model.api.data.Cast
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MovieDetailView: MvpView  {
    fun initAdapterActor()
    fun initViewMovieDetail(movieDetail: MoviesDetail)
    fun updateAdapterActor(listActor: List<Cast>)
    fun invisibleLoader()
}