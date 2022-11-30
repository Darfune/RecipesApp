package com.example.recipesapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.models.meals.full.ListOfMeals
import com.example.recipesapp.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class MealsEntity(
    var meals: ListOfMeals
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}