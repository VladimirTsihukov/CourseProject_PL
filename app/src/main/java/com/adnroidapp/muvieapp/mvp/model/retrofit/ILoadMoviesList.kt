package com.adnroidapp.muvieapp.mvp.model.retrofit

import com.adnroidapp.muvieapp.mvp.model.api.data.MovieActors
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesList
import io.reactivex.rxjava3.core.Single

interface ILoadMoviesList {
    fun retrofitLoadMoviesPopular(): Single<MoviesList>
    fun retrofitLoadMoviesTopRate(): Single<MoviesList>
    fun retrofitLoadMoviesDetail(id: Long): Single<MoviesDetail>
    fun retrofitLoadMovieActors(id: Long): Single<MovieActors>
}