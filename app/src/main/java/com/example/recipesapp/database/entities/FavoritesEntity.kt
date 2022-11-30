package com.example.recipesapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.models.meals.full.Meal
import com.example.recipesapp.utils.Constants.Companion.FAVORITES_MEALS_TABLE

@Entity(tableName = FAVORITES_MEALS_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var meal: Meal
)