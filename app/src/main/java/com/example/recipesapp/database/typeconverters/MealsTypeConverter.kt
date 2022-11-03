package com.example.recipesapp.database.typeconverters

import androidx.room.TypeConverter
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.models.meals.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealsTypeConverter {

    var gson =Gson()

    @TypeConverter
    fun mealToString(listOfMeals: ListOfMeals): String{
        return gson.toJson(listOfMeals)
    }

    @TypeConverter
    fun stringToMeal(data: String): ListOfMeals{
        val listType = object : TypeToken<ListOfMeals>() {}.type
        return gson.fromJson(data, listType)
    }
}