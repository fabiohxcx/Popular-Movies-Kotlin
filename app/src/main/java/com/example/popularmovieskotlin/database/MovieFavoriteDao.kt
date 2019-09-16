package com.example.popularmovieskotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieFavoriteDao {

    @Query("select * from entitymoviefavorite")
    fun getMoviesFavorite(): LiveData<List<EntityMovieFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: EntityMovieFavorite)

    @Delete
    fun deleteMovieFavorite(movie: EntityMovieFavorite)

    @Query("select * from entitymoviefavorite where id = :id")
    fun findMovieById(id: String): EntityMovieFavorite?

}