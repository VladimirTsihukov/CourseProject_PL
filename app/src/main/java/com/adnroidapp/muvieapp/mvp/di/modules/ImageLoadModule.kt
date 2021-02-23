package com.adnroidapp.muvieapp.mvp.di.modules

import android.widget.ImageView
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderActor
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderMovie
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorActor
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageLoadModule {
//
//    @Singleton
//    @Provides
//    fun imageLoaderMovie(): IImageLoaderMovie<ImageView> = GlideImageLoaderActorMovies()
//
//    @Singleton
//    @Provides
//    fun imageLoaderActor(): IImageLoaderActor<ImageView> = GlideImageLoaderActorActor()
}