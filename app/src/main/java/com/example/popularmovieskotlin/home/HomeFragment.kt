package com.example.popularmovieskotlin.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popularmovieskotlin.R
import com.example.popularmovieskotlin.databinding.HomeFragmentBinding
import com.example.popularmovieskotlin.network.NetworkConstants
import com.google.android.material.chip.Chip


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

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        var columNumber = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 5
            else -> 2
        }

        binding.recyclerviewMovies.layoutManager = GridLayoutManager(context, columNumber)

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

        val filters = arrayListOf<String>("Popular", "Favorites", "Top rated")

        val chipGroup = binding.filterMovies
        val inflator = LayoutInflater.from(chipGroup.context)

        val children = filters.map { filter ->
            val chip = inflator.inflate(R.layout.movie_filter, chipGroup, false) as Chip

            chip.text = filter
            chip.tag = filter
            chip.setOnCheckedChangeListener { button, checked ->
                if (checked) {
                    viewModel.getMovies(
                        when (button.tag) {
                            "Top rated" -> NetworkConstants.TOP_RATED
                            else -> NetworkConstants.POPULAR
                        }
                    )
                }
            }
            chip
        }

        chipGroup.removeAllViews()

        for (chip in children) {
            chipGroup.addView(chip)
        }

/*        binding.button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_testFragment)
        )*/
/*        Another way to navigate
        {
            findNavController().navigate(R.id.action_homeFragment_to_testFragment)
        }*/


        return binding.root
    }


}
