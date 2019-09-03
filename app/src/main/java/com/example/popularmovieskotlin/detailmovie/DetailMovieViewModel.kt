package com.example.popularmovieskotlin.detailmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.popularmovieskotlin.model.Movie

class DetailMovieViewModel(selectedMovieArg: Movie, app: Application) : AndroidViewModel(app) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie


    init {
        _selectedMovie.value = selectedMovieArg
    }

    val displayReleaseDate = Transformations.map(selectedMovie) {
        val array = selectedMovie.value!!.releaseDate?.split("-")
        "${array.get(1)}/${array.get(2)}/${array.get(0)}"
    }

}
