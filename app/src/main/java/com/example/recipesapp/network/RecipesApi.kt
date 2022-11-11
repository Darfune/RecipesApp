package com.example.recipesapp.network

import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.models.meals.Meal
import com.example.recipesapp.models.mealssmall.MealsSmallList
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface RecipesApi {

    @GET("search.php")
    suspend fun getMeals(
        @Query("s") nameOfMeal: String? = null
    ): ListOfMeals

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealsSmallList

    @GET("lookup.php?")
    suspend fun getMealById(
        @Query("i") id: String
    ): Meal

    @GET("random.php")
    suspend fun getRandomMeal(): Meal


}