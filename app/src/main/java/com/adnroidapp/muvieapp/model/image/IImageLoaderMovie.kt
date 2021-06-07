package com.adnroidapp.muvieapp.model.image

interface IImageLoaderMovie<T> {
    fun loadInto(url: String, container: T)
}