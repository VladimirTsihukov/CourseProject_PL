package com.adnroidapp.muvieapp.mvp.di.movie.module

import android.widget.ImageView
import com.adnroidapp.muvieapp.mvp.di.movie.MovieScope
import com.adnroidapp.muvieapp.mvp.model.api.ApiService
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.cache.roomcache.CacheRoomMovies
import com.adnroidapp.muvieapp.mvp.model.entity.room.db.DBMovies
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderMovie
import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.mvp.model.retrofit.RetrofitLoadMoviesList
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun imageLoaderMovie(): IImageLoaderMovie<ImageView> = GlideImageLoaderActorMovies()

    @MovieScope
    @Provides
    fun retrofitLoaderMovies(api: ApiService, networkStatus: INetworkStatus, cache: IMoviesCache)
            : ILoadMoviesList = RetrofitLoadMoviesList(api, networkStatus, cache)

    @MovieScope
    @Provides
    fun movieCache(db: DBMovies): IMoviesCache = CacheRoomMovies(db)
}