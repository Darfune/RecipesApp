package com.example.recipesapp.screens.details.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.adapters.IngredientsAdapter
import com.example.recipesapp.databinding.FragmentIngredientsBinding
import com.example.recipesapp.models.ingredients.SmallIngredient
import com.example.recipesapp.models.meals.Meal

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var ingredientsViewModel: IngredientsViewModel

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private lateinit var ingredientList: List<SmallIngredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ingredientsViewModel = ViewModelProvider(requireActivity())[IngredientsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Meal = args!!.getParcelable<Meal>("mealBundle") as Meal

        setupRecyclerView()
        setupIngredientsList(myBundle)
        ingredientList.let { ingredientsAdapter.setData(it) }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupIngredientsList(myBundle: Meal) {
        ingredientList = ingredientsViewModel.setupData(myBundle)
    }


    private fun setupRecyclerView(){
        binding.ingredientsRecyclerView.adapter = ingredientsAdapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}