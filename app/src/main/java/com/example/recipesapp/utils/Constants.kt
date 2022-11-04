package com.example.recipesapp.utils

class Constants {

    companion object{
        // Retrofit
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        // ROOM
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        // Preferences
        const val PREFERENCES_MEAL_CATEGORY = "mealCategory"
        const val PREFERENCES_MEAL_CATEGORY_ID = "mealCategoryId"
        const val PREFERENCES_NAME = "meals_preferences"
    }

}