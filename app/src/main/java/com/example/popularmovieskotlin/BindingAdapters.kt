package com.example.popularmovieskotlin

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.popularmovieskotlin.home.MovieGridAdapter
import com.example.popularmovieskotlin.model.Movie
import com.example.popularmovieskotlin.network.BASE_URL
import com.example.popularmovieskotlin.utils.MovieUrlUtils

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieGridAdapter
    adapter.submitList(movies)
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
