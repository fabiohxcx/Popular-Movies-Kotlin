package com.example.popularmovieskotlin.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Review(
    val id: String,
    val author: String,
    val content: String,
    val url: String
) : Parcelable