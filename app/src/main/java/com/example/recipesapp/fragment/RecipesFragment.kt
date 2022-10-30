package com.example.recipesapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.MainViewModel
import com.example.recipesapp.R
import com.example.recipesapp.adapters.MealsAdapter
import com.example.recipesapp.data.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mealView: View
    private lateinit var mainViewModel: MainViewModel
    private val mealsAdapter by lazy { MealsAdapter() }
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mealView = inflater.inflate(R.layout.fragment_recipes, container, false)
        recyclerView = mealView.findViewById(R.id.mealsRecyclerView)
        recyclerView.adapter = mealsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        requestApiData()

        return mealView
    }

    private fun requestApiData() {
        mainViewModel.getMeals()
        mainViewModel.mealsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { mealsAdapter.setData(it) }
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    //TODO if possible me the shimmer effect
                }
            }

        }
    }

//    private fun setupRecyclerView() {
//        recyclerView.showS
//
//    }

}