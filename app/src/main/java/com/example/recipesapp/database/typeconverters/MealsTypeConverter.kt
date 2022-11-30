package com.example.recipesapp.database.typeconverters

import androidx.room.TypeConverter
import com.example.recipesapp.models.meals.full.ListOfMeals
import com.example.recipesapp.models.meals.full.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealsTypeConverter {

    var gson =Gson()

    @TypeConverter
    fun mealToString(meal: Meal): String{
        return gson.toJson(meal)
    }

    @TypeConverter
    fun stringToMeal(data: String): Meal {
        val listType = object : TypeToken<Meal>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listOfMealsToString(listOfMeals: ListOfMeals): String{
        return gson.toJson(listOfMeals)
    }

    @TypeConverter
    fun stringToListOfMeals(data: String): ListOfMeals {
        val listType = object : TypeToken<ListOfMeals>() {}.type
        return gson.fromJson(data, listType)
    }

}