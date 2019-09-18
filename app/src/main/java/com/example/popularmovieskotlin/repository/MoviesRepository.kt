package com.example.popularmovieskotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.popularmovieskotlin.database.MoviesDatabase
import com.example.popularmovieskotlin.database.asDomainModel
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.asDatabaseModel
import com.example.popularmovieskotlin.network.MoviesApi
import com.example.popularmovieskotlin.network.NetworkConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesRepository(private val database: MoviesDatabase) {

    val movies: LiveData<List<Movie>> = Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshMovies(filter: String = NetworkConstants.POPULAR) {
        withContext(Dispatchers.IO) {

            Timber.d("filter: $filter")

            if (filter.equals(NetworkConstants.FAVORITES)) {

                val response = database.movieFavoriteDao.getMoviesFavorite()

                database.movieDao.insertAll(*response.toTypedArray()) //* -> convert List to varargs parameters

                //Deleting old values (values that constains in DB, but not contains on API response anymore)
                var idList = mutableListOf<String>()

                response.forEach {
                    idList.add(it.id)
                }

                database.movieDao.deleteOldMovies(idList.toList())

            } else {
                val response = MoviesApi.retrofitService.getMovies(filter)

                database.movieDao.insertAll(*response.asDatabaseModel()) //* -> convert List to varargs parameters

                //Deleting old values (values that constains in DB, but not contains on API response anymore)
                var idList = mutableListOf<String>()

                response.asDatabaseModel().forEach {
                    idList.add(it.id)
                }

                database.movieDao.deleteOldMovies(idList.toList())
            }


        }
    }

}
