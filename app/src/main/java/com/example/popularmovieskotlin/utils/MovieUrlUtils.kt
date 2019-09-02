package com.example.popularmovieskotlin.utils

import android.net.Uri
import android.util.Log
import timber.log.Timber
import java.net.URL

class MovieUrlUtils {

    companion object {
        const val URL_BASE = "http://image.tmdb.org/t/p/"
        const val IMG_SIZE_W92 = "w92"
        const val IMG_SIZE_W154 = "w154"
        const val IMG_SIZE_W185 = "w185"
        const val IMG_SIZE_W342 = "w342"
        const val IMG_SIZE_W500 = "w500"
        const val IMG_SIZE_W780 = "w780"
        const val IMG_SIZE_ORIGINAL = "original"

        fun buildUrlPoster(posterPath: String): String {

            val builtUri = Uri.parse(URL_BASE).buildUpon().appendPath(IMG_SIZE_W500).build()

            val url = URL(builtUri.toString())

            Timber.d("buildUrlPoster: posterPath: " + url.toString() + posterPath);

            return url.toString() + posterPath

        }
    }

}