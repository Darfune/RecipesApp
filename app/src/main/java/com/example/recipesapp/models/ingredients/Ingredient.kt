package com.example.recipesapp.models.ingredients


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("idIngredient")
    val idIngredient: String,
    @SerializedName("strDescription")
    val strDescription: String,
    @SerializedName("strIngredient")
    val strIngredient: String,
    @SerializedName("strType")
    val strType: String
)