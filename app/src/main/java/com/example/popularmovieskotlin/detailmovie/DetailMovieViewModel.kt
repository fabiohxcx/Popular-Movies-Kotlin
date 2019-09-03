package com.example.popularmovieskotlin.detailmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popularmovieskotlin.model.Movie

class DetailMovieViewModel(selectedMovie: Movie, app: Application) : AndroidViewModel(app) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = selectedMovie
    }


}
