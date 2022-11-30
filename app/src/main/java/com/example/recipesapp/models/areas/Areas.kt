package com.example.recipesapp.models.areas


import com.google.gson.annotations.SerializedName

data class Areas(
    @SerializedName("areas")
    val meals: List<Area>
)