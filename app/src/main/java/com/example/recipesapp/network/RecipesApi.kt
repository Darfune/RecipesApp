package com.example.recipesapp.network

import com.example.recipesapp.models.meals.ListOfMeals
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface RecipesApi {

    @GET("search.php?s=")
    suspend fun getMeals(): ListOfMeals

}