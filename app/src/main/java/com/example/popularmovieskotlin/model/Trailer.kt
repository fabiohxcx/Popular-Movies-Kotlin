package com.example.popularmovieskotlin.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Trailer(
    val id: String,
    val name: String,
    val size: String,
    val site: String,
    val key: String
) : Parcelable