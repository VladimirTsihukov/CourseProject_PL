package com.adnroidapp.muvieapp.mvp.di.modules

import android.widget.ImageView
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderActor
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderMovie
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorActor
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorMovies
import dagger.Module
import dagger.Provides

@Module
class ImageLoadModule {

    @Provides
    fun imageLoaderMovie(): IImageLoaderMovie<ImageView> = GlideImageLoaderActorMovies()

    @Provides
    fun imageLoaderActor(): IImageLoaderActor<ImageView> = GlideImageLoaderActorActor()
}