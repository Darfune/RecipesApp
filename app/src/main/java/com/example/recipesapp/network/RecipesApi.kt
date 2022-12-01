package com.example.recipesapp.network

import com.example.recipesapp.models.areas.Areas
import com.example.recipesapp.models.categories.Categories
import com.example.recipesapp.models.meals.full.ListOfMeals
import com.example.recipesapp.models.meals.full.Meal
import com.example.recipesapp.models.meals.shorter.ShortMealList
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface RecipesApi {

    @GET("search.php")
    suspend fun getMeals(
        @Query("s") nameOfMeal: String? = null
    ): ListOfMeals

    @GET("lookup.php?")
    suspend fun getMealById(
        @Query("i") id: String
    ): Meal

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): ShortMealList

    @GET("filter.php")
    suspend fun getMealsByArea(
        @Query("a") area: String
    ): ShortMealList

    @GET("filter.php")
    suspend fun getCategories(
        @Query("c") list: String = "list"
    ): Categories

    @GET("filter.php")
    suspend fun getAreas(
        @Query("a") list: String = "list"
    ): Areas

    @GET("random.php")
    suspend fun getRandomMeal(): Meal



}