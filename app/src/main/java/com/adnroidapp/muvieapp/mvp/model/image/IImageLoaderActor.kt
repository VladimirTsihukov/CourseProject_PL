package com.adnroidapp.muvieapp.mvp.model.image

interface IImageLoaderActor<T> {
    fun loadInto(url: String, container: T)
}