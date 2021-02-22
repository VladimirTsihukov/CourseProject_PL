package com.adnroidapp.muvieapp.mvp.model.entity.room

sealed class SealedMovies {
    object MoviePopular : SealedMovies()
    object MovieTopRate : SealedMovies()
}
