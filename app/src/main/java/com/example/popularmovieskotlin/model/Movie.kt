package com.example.popularmovieskotlin.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    val id: String,
    val title: String,
    val overview: String,

    @Json(name = "vote_average")
    val voteAverage: String,

    @Json(name = "release_date")
    val releaseDate: String,

    @Json(name = "backdrop_path")
    val backdropPath: String,

    @Json(name = "poster_path")
    val posterPath: String,

    val popularity: String
) : Parcelable