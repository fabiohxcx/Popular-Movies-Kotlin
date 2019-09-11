package com.example.popularmovieskotlin.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popularmovieskotlin.database.getDatabase
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.network.NetworkConstants
import com.example.popularmovieskotlin.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber


enum class MovieApiStatus { LOADING, ERROR, DONE }

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //Declaring LiveDatas

    private val _statusApi = MutableLiveData<MovieApiStatus>()
    val statusApi: LiveData<MovieApiStatus>
        get() = _statusApi

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database)

    init {
        getMovies()
    }

    var movies = moviesRepository.movies

    fun getMovies(filter: String = NetworkConstants.POPULAR) {

        coroutineScope.launch {
            try {
                _statusApi.value = MovieApiStatus.LOADING
                moviesRepository.refreshMovies(filter)
                _statusApi.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                Timber.d("Exception ${e.message}")
                _statusApi.value = MovieApiStatus.ERROR
            }
        }

    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
