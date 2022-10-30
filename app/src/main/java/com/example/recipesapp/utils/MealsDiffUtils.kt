package com.example.recipesapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.recipesapp.models.meals.Meal

class MealsDiffUtils(
    private val oldListOfMeals: List<Meal>,
    private val newListOfMeals: List<Meal>
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