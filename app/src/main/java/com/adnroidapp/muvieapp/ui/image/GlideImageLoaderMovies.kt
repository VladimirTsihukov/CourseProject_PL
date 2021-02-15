package com.adnroidapp.muvieapp.ui.image

import android.widget.ImageView
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoader
import com.bumptech.glide.Glide

class GlideImageLoaderMovies : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}