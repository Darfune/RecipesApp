package com.example.recipesapp.screens.details.fragments.overview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.models.meals.Meal

class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Meal = args!!.getParcelable<Meal>("mealBundle") as Meal

        view.findViewById<ImageView>(R.id.mealMainImageView).load(myBundle.strImageSource)
        view.findViewById<TextView>(R.id.selectedMealTitleTextView).text = myBundle.strMeal
        view.findViewById<TextView>(R.id.descriptionOfMealTextView).text = myBundle.strInstructions
        return view
    }
}
