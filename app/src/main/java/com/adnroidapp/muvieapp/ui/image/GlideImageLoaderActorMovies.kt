package com.adnroidapp.muvieapp.ui.image

import android.widget.ImageView
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderMovie
import com.bumptech.glide.Glide

class GlideImageLoaderActorMovies : IImageLoaderMovie<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}