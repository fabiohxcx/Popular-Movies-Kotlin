package com.example.popularmovieskotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.popularmovieskotlin.R
import com.example.popularmovieskotlin.databinding.HomeFragmentBinding
import com.example.popularmovieskotlin.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.support.v4.toast
import retrofit2.HttpException


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

        val service = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPopularMovies()

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        toast("success: " + response.body().toString())
                    } else {
                        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(activity, "Exception ${e.message}", Toast.LENGTH_LONG).show()
                } catch (e: Throwable) {
                    Toast.makeText(activity, "Ooops: Something else went wrong", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

    }

}
