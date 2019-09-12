package com.example.popularmovieskotlin.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.popularmovieskotlin.R
import com.example.popularmovieskotlin.detailmovie.TrailersAdapter
import com.example.popularmovieskotlin.home.MovieApiStatus
import com.example.popularmovieskotlin.home.MovieGridAdapter
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.model.Trailer

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieGridAdapter
    adapter.submitList(movies)
}

@BindingAdapter("listData")
fun binRecylerView(recyclerView: RecyclerView, trailer: List<Trailer>?) {
    val adapter = recyclerView.adapter as TrailersAdapter
    adapter.submitList(trailer)
}

@BindingAdapter("imageUrl")
fun bindPosterImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = MovieUrlUtils.buildUrlPoster(imgUrl).toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            ).into(imgView)
    }
}

@BindingAdapter("movieApiError")
fun bindStatusErrorApi(statusImageView: ImageView, status: MovieApiStatus?) {
    when (status) {
        MovieApiStatus.LOADING -> {
            statusImageView.visibility = View.GONE
        }
        MovieApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MovieApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }

    }
}

@BindingAdapter("movieApiLoading")
fun bindStatusLoadingApi(statusProgressBar: ProgressBar, status: MovieApiStatus?) {
    when (status) {
        MovieApiStatus.LOADING -> {
            statusProgressBar.visibility = View.VISIBLE
        }
        MovieApiStatus.ERROR -> {
            statusProgressBar.visibility = View.GONE
        }
        MovieApiStatus.DONE -> {
            statusProgressBar.visibility = View.GONE
        }

    }
}

