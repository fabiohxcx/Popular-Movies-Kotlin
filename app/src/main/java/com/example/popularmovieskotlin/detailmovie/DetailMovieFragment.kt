package com.example.popularmovieskotlin.detailmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularmovieskotlin.databinding.DetailMovieFragmentBinding
import org.jetbrains.anko.support.v4.toast

class DetailMovieFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = DetailMovieFragmentBinding.inflate(inflater)

        /* Sets the LifecycleOwner that should be used for observing changes of LiveData in this binding.
        If a LiveData is in one of the binding expressions and no LifecycleOwner is set, the LiveData will
        not be observed and updates to it will not be propagated to the UI. */
        binding.lifecycleOwner = this

        val movieSelected = DetailMovieFragmentArgs.fromBundle(arguments!!).selectedMovie

        val viewModelFactory = DetailMovieViewModelFactory(movieSelected, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailMovieViewModel::class.java)


        binding.recyclerviewTrailers.layoutManager = LinearLayoutManager(context)

        binding.recyclerviewTrailers.adapter = TrailersAdapter(TrailersAdapter.OnClickListener {
            toast("${it.name}")
        })

        return binding.root
    }

}
