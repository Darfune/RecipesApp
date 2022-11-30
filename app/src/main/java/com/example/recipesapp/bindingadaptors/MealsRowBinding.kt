package com.example.recipesapp.bindingadaptors

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.models.meals.full.Meal
import com.example.recipesapp.screens.main.fragments.recipes.RecipesFragmentDirections

class MealsRowBinding {

    companion object {

        @BindingAdapter("onMealClickListener")
        @JvmStatic
        fun onMealClickListener(mealRowLayout: ConstraintLayout, meal: Meal) {
            mealRowLayout.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(meal)
                    mealRowLayout.findNavController().navigate(action)
                } catch (exc: Exception){
                    Log.d("onMealClickListener: ", exc.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(500)
                error(R.drawable.ic_meal_placeholder)
            }
        }

    }

}