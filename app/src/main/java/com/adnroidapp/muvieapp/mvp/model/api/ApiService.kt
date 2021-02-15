package com.adnroidapp.muvieapp.mvp.model.api

import com.adnroidapp.muvieapp.mvp.model.api.ApiFactory.QUERY_PARAM_MOVIE_ID_COR
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.mvp.model.api.data.MovieActors
import com.adnroidapp.muvieapp.mvp.model.api.data.MoviesList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    fun getMoviePopular(): Single<MoviesList>

    @GET("top_rated")
    fun getMoviesTopRate(): Single<MoviesList>

    @GET("{movie_id}")
    fun getMovieDetails(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long
    ): Single<MoviesDetail>

    @GET("{movie_id}/credits")
    fun getMovieActors(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long
    ): Single<MovieActors>
}