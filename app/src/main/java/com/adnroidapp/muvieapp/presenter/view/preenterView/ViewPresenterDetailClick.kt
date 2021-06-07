package com.adnroidapp.muvieapp.presenter.view.preenterView

import com.adnroidapp.muvieapp.model.api.data.Movie

interface ViewPresenterDetailClick {
    fun clickMovie(movieId: Long)
    fun clickLikeIcon(iconLike: Boolean, movies: Movie)
}