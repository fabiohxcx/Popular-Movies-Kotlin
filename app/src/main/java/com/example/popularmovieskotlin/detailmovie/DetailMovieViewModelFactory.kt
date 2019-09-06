package com.example.popularmovieskotlin.detailmovie

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovieskotlin.model.Movie

class DetailMovieViewModelFactory(
    private val movie: Movie,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            return DetailMovieViewModel(movie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}