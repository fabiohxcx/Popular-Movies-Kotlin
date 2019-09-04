package com.example.popularmovieskotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.popularmovieskotlin.database.MoviesDatabase
import com.example.popularmovieskotlin.database.asDomainModel
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.asDatabaseModel
import com.example.popularmovieskotlin.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val database: MoviesDatabase) {

    val movies: LiveData<List<Movie>> = Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val response = MoviesApi.retrofitService.getPopularMovies()
            database.movieDao.insertAll(*response.asDatabaseModel()) //* -> convert List to varargs parameters
        }
    }

}