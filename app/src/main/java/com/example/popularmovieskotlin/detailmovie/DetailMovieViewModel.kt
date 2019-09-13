package com.example.popularmovieskotlin.detailmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.Review
import com.example.popularmovieskotlin.model.Trailer
import com.example.popularmovieskotlin.network.MoviesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _selectedMovie.value = selectedMovieArg

        coroutineScope.launch {
            try {

                selectedMovie.value?.let {
                    val result = MoviesApi.retrofitService.getTrailers(it.id)
                    _trailers.value = result.trailers

                    val resultReviews = MoviesApi.retrofitService.getReviews(it.id)
                    _reviews.value = resultReviews.results
                    Timber.d("Reviews: " + resultReviews.results.toString())
                }

            } catch (e: Exception) {
                Timber.d("Exception ${e.message}")
            }
        }

    }

    val displayReleaseDate = Transformations.map(selectedMovie) {
        val array = selectedMovie.value!!.releaseDate.split("-")
        "${array.get(1)}/${array.get(2)}/${array.get(0)}"
    }

}
