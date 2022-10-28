package com.example.recipesapp.repository

import android.util.Log
import com.example.recipesapp.data.Resource
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.network.RecipesApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RecipesRepository @Inject constructor(
    private val recipesApi: RecipesApi
){
    suspend fun getMeals(): Resource<ListOfMeals> {
        val response = try {
            recipesApi.getMeals()
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getMeals: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

}