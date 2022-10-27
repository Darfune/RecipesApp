package com.example.recipesapp.models.meals


import com.google.gson.annotations.SerializedName

data class ListOfMeals(
    @SerializedName("meals")
    val meals: List<Meal>
)