package com.example.popularmovieskotlin.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultTrailers(
    val id: String,

    @Json(name = "results")
    val trailers: List<Trailer>
)