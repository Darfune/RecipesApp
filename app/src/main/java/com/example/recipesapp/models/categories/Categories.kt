package com.example.recipesapp.models.categories


import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("categories")
    val categories: List<Category>
)