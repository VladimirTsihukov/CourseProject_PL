package com.adnroidapp.muvieapp.mvp.model.api.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesDetail(
    val id: Long,

    val title: String,

    val overview: String,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Long,

    val adult: Boolean,

    val runtime: Long,

    val genres: List<Genre>,
): Parcelable

@Parcelize
data class BelongsToCollection(
    val id: Long,
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
): Parcelable

@Parcelize
data class Genre(
    val id: Long,
    val name: String,
): Parcelable