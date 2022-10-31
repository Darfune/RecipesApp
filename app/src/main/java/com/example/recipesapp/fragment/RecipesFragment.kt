package com.example.recipesapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.MainViewModel
import com.example.recipesapp.R
import com.example.recipesapp.adapters.MealsAdapter
import com.example.recipesapp.data.Resource
import com.example.recipesapp.models.meals.ListOfMeals
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mealView: View
    private lateinit var mainViewModel: MainViewModel
    private val mealsAdapter by lazy { MealsAdapter() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var noConnectionTextView: TextView
    private lateinit var noConnectionImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mealView = inflater.inflate(R.layout.fragment_recipes, container, false)

        shimmerLayout = mealView.findViewById(R.id.mealsShimmerLayout)

        recyclerView = mealView.findViewById(R.id.mealsRecyclerView)
        recyclerView.adapter = mealsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        noConnectionTextView = mealView.findViewById(R.id.noConnectionTextView)
        noConnectionImageView = mealView.findViewById(R.id.noConnectionImageView)


        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        requestApiData()

        return mealView
    }

    private fun requestApiData() {
        mainViewModel.getMeals()
        mainViewModel.mealsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {

                    setupRecyclerView(response)
                }
                is Resource.Error -> {
                    showErrorView(response)


                }
                is Resource.Loading -> {

                }
            }

        }
    }

    private fun showErrorView(response: Resource.Error<ListOfMeals>) {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
        noConnectionTextView.visibility = View.VISIBLE
        noConnectionImageView.visibility = View.VISIBLE
        Toast.makeText(
            requireContext(),
            response.message.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupRecyclerView(response: Resource.Success<ListOfMeals>) {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
        response.data?.let { mealsAdapter.setData(it) }
        recyclerView.visibility = View.VISIBLE
    }

}