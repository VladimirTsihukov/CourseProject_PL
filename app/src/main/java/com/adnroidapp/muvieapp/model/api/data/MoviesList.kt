package com.adnroidapp.muvieapp.model.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesList(
    val results: List<Movie>,
): Parcelable
