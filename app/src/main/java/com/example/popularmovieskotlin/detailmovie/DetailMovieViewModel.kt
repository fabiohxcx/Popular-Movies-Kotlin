package com.example.popularmovieskotlin.detailmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.popularmovieskotlin.database.asDatabaseModel
import com.example.popularmovieskotlin.database.getDatabase
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.Review
import com.example.popularmovieskotlin.model.Trailer
import com.example.popularmovieskotlin.network.MoviesApi
import kotlinx.coroutines.*
import timber.log.Timber

class DetailMovieViewModel(selectedMovieArg: Movie, app: Application) : AndroidViewModel(app) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>>
        get() = _trailers

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>>
        get() = _reviews

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean>
        get() = _favorite

    private val database = getDatabase(app)

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineUiScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _selectedMovie.value = selectedMovieArg

        coroutineUiScope.launch {
            try {

                selectedMovie.value?.let {
                    val result = MoviesApi.retrofitService.getTrailers(it.id)
                    _trailers.value = result.trailers

                    val resultReviews = MoviesApi.retrofitService.getReviews(it.id)
                    _reviews.value = resultReviews.results
                    //Timber.d("Reviews: " + resultReviews.results.toString())

                    _favorite.value = getFavorite(it.id)

                }

            } catch (e: Exception) {
                Timber.d("Exception ${e.message}")
            }
        }


    }


    private suspend fun getFavorite(id: String): Boolean {
        return withContext(Dispatchers.IO) {

            val favoriteMovie = database.movieFavoriteDao.findMovieById(id)

            Timber.d("favoriteMovie = $favoriteMovie")

            favoriteMovie != null
        }
    }

    fun clickFavorite() {
        CoroutineScope(viewModelJob + Dispatchers.IO).launch {
            selectedMovie.value?.let {
                database.movieFavoriteDao.insertAll(it.asDatabaseModel())
            }
        }

    }

    val displayReleaseDate = Transformations.map(selectedMovie) {
        val array = selectedMovie.value!!.releaseDate.split("-")
        "${array.get(1)}/${array.get(2)}/${array.get(0)}"
    }

}
