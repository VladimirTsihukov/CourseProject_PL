package com.adnroidapp.muvieapp.model.retrofit

import com.adnroidapp.muvieapp.model.EnumTypeMovie
import com.adnroidapp.muvieapp.model.api.data.Movie
import io.reactivex.Single

interface ILoadMoviesList {
    fun loadMovieInServer(movieType: EnumTypeMovie): Single<List<Movie>>
}