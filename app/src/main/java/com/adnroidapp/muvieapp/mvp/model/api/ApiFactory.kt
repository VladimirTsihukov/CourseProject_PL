package com.adnroidapp.muvieapp.mvp.model.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/"
    private const val API_KEY_ID = "7d9db2e12493542315f5bcb0f3f0de61"
    private const val API_KEY = "api_key"
    private const val QUERY_PARAM_LANGUAGE = "language"
    private const val LANGUAGE_RUS = "ru"
    const val QUERY_PARAM_MOVIE_ID_COR = "movie_id"
    const val BASE_URL_MOVIE_IMAGE = "https://image.tmdb.org/t/p/w500/"

    private val authInterceptor = Interceptor {chain ->
            val newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter(API_KEY, API_KEY_ID)
                .addQueryParameter(QUERY_PARAM_LANGUAGE, LANGUAGE_RUS)
                .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val tmdClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofitMovie =  Retrofit.Builder()
        .client(tmdClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL_MOVIE)
        .build()

    val apiServiceMovies: ApiService = retrofitMovie.create(ApiService::class.java)
}