package com.example.popularmovieskotlin.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popularmovieskotlin.databinding.HomeFragmentBinding


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = HomeFragmentBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        var columNumber = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 5
            else -> 2
        }

        binding.recyclerviewMovies.layoutManager = GridLayoutManager(context, columNumber);

        binding.recyclerviewMovies.adapter = MovieGridAdapter(MovieGridAdapter.OnClickListener { movie ->
            viewModel.displayMovieDetails(movie)
        })


        viewModel.navigateToSelectedMovie.observe(
            this, Observer {
                if (null != it) {
                    this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it))
                    viewModel.displayMovieDetailsComplete()
                }
            }
        )
/*        binding.button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_testFragment)
        )*/
/*        Another way to navigate
        {
            findNavController().navigate(R.id.action_homeFragment_to_testFragment)
        }*/

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
