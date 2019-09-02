package com.example.popularmovieskotlin.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.ResultMovies
import com.example.popularmovieskotlin.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class HomeViewModel : ViewModel() {

    //Declaring LiveDatas
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _resultMovies = MutableLiveData<ResultMovies>()
    val resultMovies: LiveData<ResultMovies>
        get() = _resultMovies

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMovies()
    }

    private fun getMovies() {
        val service = RetrofitFactory.makeRetrofitService()

        coroutineScope.launch {
            val response = service.getPopularMovies()

            try {
                Timber.d("success ${response}")

                _resultMovies.value = response

                _resultMovies?.let {
                    _movies.value = it.value?.listMovies
                }

            } catch (e: HttpException) {
                Timber.d("Exception ${e.message}")
            } catch (e: Throwable) {
                Timber.d("Ooops: Something else went wrong ${e.message}")
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
