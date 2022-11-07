package com.example.recipesapp.fragment.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.MainViewModel
import com.example.recipesapp.R
import com.example.recipesapp.adapters.MealsAdapter
import com.example.recipesapp.data.Resource
import com.example.recipesapp.databinding.FragmentRecipesBinding
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mealsViewModel: MealsViewModel
    private val mealsAdapter by lazy { MealsAdapter() }

    private val args by navArgs<RecipesFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mealsViewModel = ViewModelProvider(requireActivity())[MealsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        initRecyclerView()
        readDatabase()

        binding.mealsTypeFloatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_mealsBottomSheet)
        }

        return binding.root
    }

    private fun initRecyclerView() {
        binding.mealsRecyclerView.adapter = mealsAdapter
        binding.mealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readMeals.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase: called")
                    mealsAdapter.setData(database[0].meals)
                    showViewItems(true)
                }
                else if (database.isEmpty() && args.backFromBottomSheet)
                else requestApiData()
            }
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData: called")
        mainViewModel.getMeals()
        mainViewModel.mealsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> setupRecyclerView(response)
                is Resource.Error -> showErrorView(response)
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readMeals.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) mealsAdapter.setData(database[0].meals)
            }
        }
    }

    private fun showErrorView(response: Resource.Error<ListOfMeals>) {
        showViewItems(false)
        Toast.makeText(
            requireContext(),
            response.message.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupRecyclerView(response: Resource.Success<ListOfMeals>) {
        response.data?.let { mealsAdapter.setData(it) }
        showViewItems(true)
    }

    private fun showViewItems(connection: Boolean) {
        binding.mealsShimmerLayout.stopShimmer()
        binding.mealsShimmerLayout.visibility = View.GONE
        if (connection) {
            binding.mealsRecyclerView.visibility = View.VISIBLE
        } else {
            loadDataFromCache()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}