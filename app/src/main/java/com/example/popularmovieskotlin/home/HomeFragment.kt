package com.example.popularmovieskotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.popularmovieskotlin.R
import com.example.popularmovieskotlin.databinding.HomeFragmentBinding


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_testFragment)
        )
/*        Another way to navigate
        {
            findNavController().navigate(R.id.action_homeFragment_to_testFragment)
        }*/

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

    }

}
