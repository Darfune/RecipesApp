package com.example.recipesapp.models.meals.shorter


import com.google.gson.annotations.SerializedName

data class ShortMealList(
    @SerializedName("meals")
    val meals: List<ShortMeal>
)