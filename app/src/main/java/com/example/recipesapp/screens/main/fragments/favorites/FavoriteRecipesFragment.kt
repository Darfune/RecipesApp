package com.example.recipesapp.screens.main.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.adapters.FavoriteMealsAdapter
import com.example.recipesapp.databinding.FragmentFavoriteRecipesBinding
import com.example.recipesapp.screens.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val favoriteMealsAdapter: FavoriteMealsAdapter by lazy {
        FavoriteMealsAdapter(
            requireActivity(),
            mainViewModel
        )
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.favoriteMealsAdapter = favoriteMealsAdapter

        setMenuProvider()

        setupRecyclerView(binding.favoriteMealsRecyclerView)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupRecyclerView(favoriteMealsRecyclerView: RecyclerView) {
        favoriteMealsRecyclerView.adapter = favoriteMealsAdapter
        favoriteMealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
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
                menuInflater.inflate(R.menu.favorite_meals_menu, menu)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.delete_all_favorite_meals) {
                    mainViewModel.deleteAllFavoriteMeal()
                    showSnackBar()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            "All meals removed",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        favoriteMealsAdapter.clearContextualMode()
    }
}