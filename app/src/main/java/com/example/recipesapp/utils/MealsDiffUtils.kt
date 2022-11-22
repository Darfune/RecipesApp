package com.example.recipesapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.recipesapp.models.meals.Meal

class MealsDiffUtils<T>(
    private val oldListOfMeals: List<T>,
    private val newListOfMeals: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldListOfMeals.size
    }

    override fun getNewListSize(): Int {
        return newListOfMeals.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListOfMeals[oldItemPosition] == newListOfMeals[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListOfMeals[oldItemPosition] == newListOfMeals[newItemPosition]
    }
}