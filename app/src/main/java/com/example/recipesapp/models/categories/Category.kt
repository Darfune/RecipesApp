package com.example.recipesapp.models.categories


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("strCategory")
    val strCategory: String
)