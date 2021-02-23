package com.adnroidapp.muvieapp.mvp.view.preenterView

import com.adnroidapp.muvieapp.mvp.model.api.data.Movie

interface PresenterDetailViewClick {
    fun clickMovie(movieId: Long)
    fun clickLikeIcon(iconLike: Boolean, movies: Movie)
}