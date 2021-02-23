package com.adnroidapp.muvieapp.mvp.di.modules

import com.adnroidapp.muvieapp.mvp.model.api.ApiService
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesDetailCache
import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesDetail
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.mvp.model.retrofit.RetrofitLoadMovieDetail
import com.adnroidapp.muvieapp.mvp.model.retrofit.RetrofitLoadMoviesList
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

//    @Singleton
//    @Provides
//    fun retrofitLoaderMovies(api: ApiService, networkStatus: INetworkStatus, cache: IMoviesCache)
//    : ILoadMoviesList = RetrofitLoadMoviesList(api, networkStatus, cache)
//
//    @Singleton
//    @Provides
//    fun retrofitLoadDetail(api: ApiService, networkStatus: INetworkStatus, cache: IMoviesDetailCache)
//    : ILoadMoviesDetail = RetrofitLoadMovieDetail(api, networkStatus, cache)

}