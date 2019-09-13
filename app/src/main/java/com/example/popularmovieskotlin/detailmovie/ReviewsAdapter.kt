package com.example.popularmovieskotlin.detailmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovieskotlin.databinding.ReviewViewItemBinding
import com.example.popularmovieskotlin.model.Review

class ReviewsAdapter : ListAdapter<Review, ReviewsAdapter.ReviewsViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ReviewsViewHolder(private var binding: ReviewViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.review = review
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapter.ReviewsViewHolder {
        return ReviewsViewHolder(ReviewViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ReviewsViewHolder, position: Int) {

        val review = getItem(position)
        holder.bind(review)

    }

}