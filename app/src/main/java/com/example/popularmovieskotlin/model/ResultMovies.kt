package com.example.popularmovieskotlin.model

import com.squareup.moshi.Json


data class ResultMovies(
    val page: String,

    @Json(name = "total_results")
    val totalResults: String,

    @Json(name = "total_pages")
    val totalPages: String,

    @Json(name = "results")
    val listMovies: List<Movie>
)