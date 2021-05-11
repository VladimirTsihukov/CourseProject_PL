package com.adnroidapp.muvieapp.presenter.view

import com.adnroidapp.muvieapp.model.api.data.Cast
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewMovieDetail: MvpView  {

    fun initViewMovieDetail(movieDetail: RoomDetailMovie)
    fun updateAdapterActor(listActor: List<Cast>)
    fun invisibleLoader()
    fun release()
}