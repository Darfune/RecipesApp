package com.example.recipesapp.models.ingredients


import com.google.gson.annotations.SerializedName

data class ListOfIngredients(
    @SerializedName("ingredient")
    val ingredients: List<Ingredient>
)