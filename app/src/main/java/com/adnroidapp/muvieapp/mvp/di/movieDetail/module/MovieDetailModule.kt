package com.adnroidapp.muvieapp.mvp.di.movieDetail.module

import android.widget.ImageView
import com.adnroidapp.muvieapp.mvp.di.movieDetail.MovieDetailScope
import com.adnroidapp.muvieapp.mvp.model.api.ApiService
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesDetailCache
import com.adnroidapp.muvieapp.mvp.model.cache.roomcache.CacheRoomMovieDetail
import com.adnroidapp.muvieapp.mvp.model.entity.room.db.DBMovies
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderActor
import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesDetail
import com.adnroidapp.muvieapp.mvp.model.retrofit.RetrofitLoadMovieDetail
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorActor
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule {

    @MovieDetailScope
    @Provides
    fun imageLoaderActor(): IImageLoaderActor<ImageView> = GlideImageLoaderActorActor()

    @MovieDetailScope
    @Provides
    fun retrofitLoadDetail(api: ApiService, networkStatus: INetworkStatus, cache: IMoviesDetailCache)
            : ILoadMoviesDetail = RetrofitLoadMovieDetail(api, networkStatus, cache)

    @MovieDetailScope
    @Provides
    fun movieDetailCache(db: DBMovies): IMoviesDetailCache = CacheRoomMovieDetail(db)
}