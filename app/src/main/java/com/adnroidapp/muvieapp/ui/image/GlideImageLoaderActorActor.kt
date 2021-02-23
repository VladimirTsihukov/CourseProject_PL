package com.adnroidapp.muvieapp.ui.image

import android.widget.ImageView
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.model.image.IImageLoaderActor
import com.bumptech.glide.Glide

class GlideImageLoaderActorActor: IImageLoaderActor<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .error(R.drawable.placeholder)
            .into(container)
    }
}