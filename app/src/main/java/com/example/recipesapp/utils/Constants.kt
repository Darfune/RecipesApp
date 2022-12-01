package com.example.recipesapp.utils

class Constants {

    companion object{
        // Retrofit
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        // Base For Images
        const val BASE_IMG_URL = "https://www.themealdb.com/images/"
        const val END_IMG_URL = ".png"
        // For Ingredients
        const val ING_IMG_URL = "ingredients/"
        // For Categories
        const val CTG_IMG_URL = "category/"
        // For Flags
        const val FLG_IMG_URL = "icons/flags/big"
        const val FLG_M_IMG_SIZE = "/64/"
        const val FLG_L_IMG_SIZE = "/128/"
        const val END_FLG_IMG_URL = ".png"

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