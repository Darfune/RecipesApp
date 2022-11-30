package com.example.recipesapp.models.ingredients.shorter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class SmallIngredient (
    @SerializedName("strIngredientName")
    val strIngredientName: String?,
    @SerializedName("strMeasure")
    val strMeasure: String?
) : Parcelable