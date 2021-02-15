package com.adnroidapp.muvieapp.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}