package com.example.popularmovieskotlin.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultReviews(
    val id: String,
    val page: String,
    val results: List<Review>
)