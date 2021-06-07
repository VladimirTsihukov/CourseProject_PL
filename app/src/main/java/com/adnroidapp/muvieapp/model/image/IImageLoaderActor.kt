package com.adnroidapp.muvieapp.model.image

interface IImageLoaderActor<T> {
    fun loadInto(url: String, container: T)
}