package com.example.popularmovieskotlin.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovieskotlin.databinding.GridViewItemBinding
import com.example.popularmovieskotlin.model.Movie
import timber.log.Timber

class MovieGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<Movie, MovieGridAdapter.MovieViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        Timber.d("${movie.posterPath}")
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }


    class MovieViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }

    }


}