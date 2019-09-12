package com.example.popularmovieskotlin.detailmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovieskotlin.databinding.TrailersViewItemBinding
import com.example.popularmovieskotlin.model.Trailer
import timber.log.Timber

class TrailersAdapter(private val onClickListener: TrailersAdapter.OnClickListener) :
    ListAdapter<Trailer, TrailersAdapter.TrailerViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersAdapter.TrailerViewHolder {
        return TrailerViewHolder(TrailersViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = getItem(position)
        holder.bind(trailer)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(trailer)
        }
        Timber.d("${trailer.name}")
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class TrailerViewHolder(private var binding: TrailersViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trailer: Trailer) {
            binding.trailer = trailer
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (trailer: Trailer) -> Unit) {
        fun onClick(trailer: Trailer) = clickListener(trailer)
    }

}
