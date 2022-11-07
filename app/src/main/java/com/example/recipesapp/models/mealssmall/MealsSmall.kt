package com.example.recipesapp.models.mealssmall


import com.google.gson.annotations.SerializedName

data class MealsSmall(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)