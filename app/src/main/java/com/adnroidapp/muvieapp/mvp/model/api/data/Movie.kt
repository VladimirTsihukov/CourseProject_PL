package com.adnroidapp.muvieapp.mvp.model.api.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(

    val id: Long,

    val title: String,

    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Long,

    @SerializedName("genre_ids")
    val genreIDS: List<Long>,

    val adult: Boolean,

    var likeMovies: Boolean = false,

    ) : Parcelable
