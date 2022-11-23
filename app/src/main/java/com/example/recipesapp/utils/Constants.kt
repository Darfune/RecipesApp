package com.example.recipesapp.utils

class Constants {

    companion object{
        // Retrofit
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        const val BASE_ING_IMG_URL = "https://www.themealdb.com/images/ingredients/"
        const val END_SMALL_IMG_URL = "-Small.png"

        // ROOM
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITES_MEALS_TABLE = "favorites_meals_table"

        // Preferences
        const val PREFERENCES_MEAL_CATEGORY = "mealCategory"
        const val PREFERENCES_MEAL_CATEGORY_ID = "mealCategoryId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

        const val PREFERENCES_NAME = "meals_preferences"

        const val DEFAULT_MEAL_CATEGORY = "beef"
    }

}