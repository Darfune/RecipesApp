package com.example.recipesapp.models.meals.full


import com.google.gson.annotations.SerializedName

data class ListOfMeals(
    @SerializedName("meals")
    val meals: List<Meal>
) {
    operator fun get(i: Int): Meal {
        return meals[i]
    }

    fun size(): Int = meals.size
}