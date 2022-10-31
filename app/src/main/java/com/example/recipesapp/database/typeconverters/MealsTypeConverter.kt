package com.example.recipesapp.database.typeconverters

import androidx.room.TypeConverter
import com.example.recipesapp.models.meals.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealsTypeConverter {

    var gson =Gson()

    @TypeConverter
    fun mealToString(meal: Meal): String{
        return gson.toJson(meal)
    }

    @TypeConverter
    fun stringToMeal(data: String): Meal{
        val listType = object : TypeToken<Meal>() {}.type
        return gson.fromJson(data, listType)
    }
}