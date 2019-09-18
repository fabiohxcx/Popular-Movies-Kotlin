package com.example.popularmovieskotlin.database

import androidx.room.*

@Dao
interface MovieFavoriteDao {

    @Query("select * from entitymoviefavorite")
    fun getMoviesFavorite(): List<EntityMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: EntityMovieFavorite)

    @Delete
    fun deleteMovieFavorite(movie: EntityMovieFavorite)

    @Query("select * from entitymoviefavorite where id = :id")
    fun findMovieById(id: String): EntityMovieFavorite?

}