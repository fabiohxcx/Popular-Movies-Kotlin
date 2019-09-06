package com.example.popularmovieskotlin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("select * from entitymovie")
    fun getMovies(): LiveData<List<EntityMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: EntityMovie)

    @Query("DELETE FROM entitymovie WHERE EntityMovie.id NOT IN(:listIdMovies)")
    fun deleteOldMovies(listIdMovies: List<String>)

}