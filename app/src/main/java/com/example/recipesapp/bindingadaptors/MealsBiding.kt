package com.example.recipesapp.bindingadaptors

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.recipesapp.data.Resource
import com.example.recipesapp.database.entities.MealsEntity
import com.example.recipesapp.models.meals.full.ListOfMeals

class MealsBiding {


    companion object {
        @BindingAdapter("readApiResponseImageView", "readDatabaseImageView", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            noConnectionImageView: ImageView,
            apiResponse: Resource<ListOfMeals>?,
            database: List<MealsEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty())
                noConnectionImageView.visibility = View.VISIBLE
            else if (apiResponse is Resource.Loading)
                noConnectionImageView.visibility = View.INVISIBLE
            else if (apiResponse is Resource.Success)
                noConnectionImageView.visibility = View.INVISIBLE
        }

        @BindingAdapter("readApiResponseTextView", "readDatabaseTextView", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            noConnectionTextView: TextView,
            apiResponse: Resource<ListOfMeals>?,
            database: List<MealsEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty())
                noConnectionTextView.visibility = View.VISIBLE
            else if (apiResponse is Resource.Loading)
                noConnectionTextView.visibility = View.INVISIBLE
            else if (apiResponse is Resource.Success)
                noConnectionTextView.visibility = View.INVISIBLE
        }
    }
}