package com.example.recipesapp.screens.details.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.recipesapp.databinding.FragmentOverviewBinding
import com.example.recipesapp.models.meals.Meal

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Meal = args!!.getParcelable<Meal>("mealBundle") as Meal

        binding.mealMainImageView.load(myBundle.strMealThumb)
        binding.selectedMealTitleTextView.text = myBundle.strMeal
        binding.descriptionOfMealTextView.text = myBundle.strInstructions

        // Inflate the layout for this fragment
        return binding.root
    }
}
