package com.example.recipesapp.screens.main.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.adapters.FavoriteMealsAdapter
import com.example.recipesapp.databinding.FragmentFavoriteRecipesBinding
import com.example.recipesapp.screens.main.MainViewModel
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

        setupRecyclerView(binding.favoriteMealsRecyclerView)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupRecyclerView(favoriteMealsRecyclerView: RecyclerView) {
        favoriteMealsRecyclerView.adapter = favoriteMealsAdapter
        favoriteMealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        favoriteMealsAdapter.clearContextualMode()
    }
}