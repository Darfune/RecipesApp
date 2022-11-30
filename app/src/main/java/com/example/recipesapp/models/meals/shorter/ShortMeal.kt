package com.example.recipesapp.models.meals.shorter


import com.google.gson.annotations.SerializedName

data class ShortMeal(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)