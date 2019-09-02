package com.example.popularmovieskotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.popularmovieskotlin.R
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

        binding.rvMovies.adapter = MovieGridAdapter()

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
