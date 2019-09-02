package com.example.popularmovieskotlin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovieskotlin.BuildConfig
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.ResultMovies
import com.example.popularmovieskotlin.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber


enum class MovieApiStatus { LOADING, ERROR, DONE }

class HomeViewModel : ViewModel() {

    //Declaring LiveDatas
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _resultMovies = MutableLiveData<ResultMovies>()
    val resultMovies: LiveData<ResultMovies>
        get() = _resultMovies

    private val _statusApi = MutableLiveData<MovieApiStatus>()
    val statusApi: LiveData<MovieApiStatus>
        get() = _statusApi

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMovies()
    }

    private fun getMovies() {

        coroutineScope.launch {
            try {
                _statusApi.value = MovieApiStatus.LOADING
                val response = RetrofitFactory.MoviesApi.retrofitService.getPopularMovies()

                Timber.d("success ${response}")

                _resultMovies.value = response

                _resultMovies?.let {
                    _movies.value = it.value?.listMovies
                }
                _statusApi.value = MovieApiStatus.DONE

            } catch (e: Exception) {
                Timber.d("Exception ${e.message}")
                _statusApi.value = MovieApiStatus.ERROR
                _movies.value = ArrayList()
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
