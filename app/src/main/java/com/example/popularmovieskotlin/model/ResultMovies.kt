package com.example.popularmovieskotlin.model

import com.example.popularmovieskotlin.database.EntityMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultMovies(
    val page: String,

    @Json(name = "total_results")
    val totalResults: String,

    @Json(name = "total_pages")
    val totalPages: String,

    @Json(name = "results")
    val listMovies: List<Movie>
)

fun ResultMovies.asDomainModel(): List<Movie> {
    return listMovies.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            voteAverage = it.voteAverage,
            releaseDate = it.releaseDate,
            backdropPath = it.backdropPath,
            posterPath = it.posterPath,
            popularity = it.popularity
        )
    }
}

fun ResultMovies.asDatabaseModel(): Array<EntityMovie> {
    return listMovies.map {
        EntityMovie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            voteAverage = it.voteAverage,
            releaseDate = it.releaseDate,
            backdropPath = it.backdropPath,
            posterPath = it.posterPath,
            popularity = it.popularity
        )
    }.toTypedArray()
}