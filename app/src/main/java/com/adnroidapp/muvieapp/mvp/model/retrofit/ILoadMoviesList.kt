package com.adnroidapp.muvieapp.mvp.model.retrofit

import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import io.reactivex.rxjava3.core.Single

interface ILoadMoviesList {
    fun retrofitLoadMoviesPopular(): Single<List<Movie>>
    fun retrofitLoadMoviesTopRate(): Single<List<Movie>>
}