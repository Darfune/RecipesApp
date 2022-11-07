package com.example.recipesapp.models.mealssmall


import com.google.gson.annotations.SerializedName

data class MealsSmallList(
    @SerializedName("mealSmalls")
    val mealSmalls: List<MealsSmall>
)