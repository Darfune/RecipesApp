package com.example.recipesapp.repository

import android.util.Log
import com.example.recipesapp.data.Resource
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.models.meals.Meal
import com.example.recipesapp.models.mealssmall.MealsSmallList
import com.example.recipesapp.network.RecipesApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RecipesRepository @Inject constructor(
    private val recipesApi: RecipesApi
) {
    suspend fun getMeals(name: String? = null): Resource<ListOfMeals> {
        val response = try {
            recipesApi.getMeals(nameOfMeal = name)
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getMealSmalls: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getMealsByCategory(category: String): Resource<MealsSmallList> {
        val response = try {
            recipesApi.getMealsByCategory(category)
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getMealsByCategory: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getMealById(id: String): Resource<Meal> {
        val response = try {
            recipesApi.getMealById(id)
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getMealsByCategory: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

}