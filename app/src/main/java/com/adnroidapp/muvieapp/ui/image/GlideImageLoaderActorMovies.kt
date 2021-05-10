package com.adnroidapp.muvieapp.ui.image

import android.widget.ImageView
import com.adnroidapp.muvieapp.ClassKey.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderMovie
import com.bumptech.glide.Glide

class GlideImageLoaderActorMovies : IImageLoaderMovie<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(BASE_URL_MOVIE_IMAGE + url)
            .error(R.drawable.ic_ph_movie_grey_200)
            .into(container)
    }
}