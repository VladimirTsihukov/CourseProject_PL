package com.adnroidapp.muvieapp.mvp.model.retrofit

import com.adnroidapp.muvieapp.mvp.model.EnumTypeMovie
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import io.reactivex.rxjava3.core.Single

interface ILoadMoviesList {
    fun loadMovieInServer(movieType: EnumTypeMovie): Single<List<Movie>>
}