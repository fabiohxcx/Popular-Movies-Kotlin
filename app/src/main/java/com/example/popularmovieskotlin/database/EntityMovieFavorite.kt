package com.example.popularmovieskotlin.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.popularmovieskotlin.model.Movie

@Entity
data class EntityMovieFavorite constructor(
    @PrimaryKey
    val id: String,
    val title: String,
    val overview: String,
    val voteAverage: String,
    val releaseDate: String,
    val backdropPath: String,
    val posterPath: String,
    val popularity: String
)

fun List<EntityMovieFavorite>.asDomainModel(): List<Movie> {
    return map {
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

fun EntityMovieFavorite.asDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        backdropPath = backdropPath,
        posterPath = posterPath,
        popularity = popularity
    )
}

fun Movie.asDatabaseModel(): EntityMovieFavorite {
    return EntityMovieFavorite(
        id = id,
        title = title,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        backdropPath = backdropPath,
        posterPath = posterPath,
        popularity = popularity
    )
}