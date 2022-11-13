package com.example.recipesapp.fragment.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
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
import com.example.recipesapp.utils.NetworkListener
//import com.example.recipesapp.repository.Requests
import com.example.recipesapp.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mealsViewModel: MealsViewModel
    private val mealsAdapter by lazy { MealsAdapter() }

    private lateinit var networkListener: NetworkListener

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

        setMenuProvider()

        initRecyclerView()

        mealsViewModel.readBackOnline.observe(viewLifecycleOwner) {
            mealsViewModel.backOnline = it
        }

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("RecipesFragment", "NetworkListener: $status")
                    mealsViewModel.networkStatus = status
                    mealsViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.mealsTypeFloatingButton.setOnClickListener {
            if (mealsViewModel.networkStatus)
                findNavController().navigate(R.id.action_recipesFragment_to_mealsBottomSheet)
            else mealsViewModel.showNetworkStatus()
        }

        return binding.root
    }

    private fun setMenuProvider() {
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.meals_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@RecipesFragment)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchApiData(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("RecipesFragment", "onQueryTextChange: $newText")
//        if (newText != null) searchApiData(newText)
//        else requestApiData()
        return true
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
                } //else if (database.isEmpty() && args.backFromBottomSheet)
                else requestApiData()
            }
        }
    }

    private fun requestApiData() {

        mainViewModel.getMeals()
        Log.d("RecipesFragment", "requestApiData: called")
        mainViewModel.mealsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> setupRecyclerView(response)
                is Resource.Error -> showErrorView(response)
                is Resource.Loading -> loadShimmerEffect()
            }
        }
    }

    private fun searchApiData(searchMealsQuery: String) {

        mainViewModel.searchMeals(mealsViewModel.applySearchQuery(searchQuery = searchMealsQuery))
        Log.d("RecipesFragment", "requestApiData: called")
        mainViewModel.searchedMealsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> setupRecyclerView(response)
                is Resource.Error -> showErrorView(response)
                is Resource.Loading -> loadShimmerEffect()
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

    private fun loadShimmerEffect() {
        binding.mealsRecyclerView.visibility = View.GONE
        binding.mealsShimmerLayout.startShimmer()
        binding.mealsShimmerLayout.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}