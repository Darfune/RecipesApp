package com.example.recipesapp.repository

import android.util.Log
import com.example.recipesapp.data.Resource
import com.example.recipesapp.models.areas.Areas
import com.example.recipesapp.models.categories.Categories
import com.example.recipesapp.models.meals.full.ListOfMeals
import com.example.recipesapp.models.meals.shorter.ShortMealList
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

    suspend fun getCategories(): Resource<Categories> {
        val response = try {
            recipesApi.getCategories()
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getCategories: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getAreas(): Resource<Areas> {
        val response = try {
            recipesApi.getAreas()
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getAreas: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getMealsByCategory(category: String): Resource<ShortMealList> {
        val response = try {
            recipesApi.getMealsByCategory(category)
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getMealsByCategory: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getMealsByArea(area: String): Resource<ShortMealList> {
        val response = try {
            recipesApi.getMealsByArea(area)
        } catch (exc: Exception) {
            Log.d("Recipes Repository", "getMealsByArea: $exc")
            return Resource.Error("Unexpected Error occurred.")
        }
        return Resource.Success(response)
    }

//    suspend fun getMealById(id: String): Resource<Area> {
//        val response = try {
//            recipesApi.getMealById(id)
//        } catch (exc: Exception) {
//            Log.d("Recipes Repository", "getMealsByCategory: $exc")
//            return Resource.Error("Unexpected Error occurred.")
//        }
//        return Resource.Success(response)
//    }
//
//    suspend fun getRandomMeal(): Resource<Area> {
//        val response = try {
//            recipesApi.getRandomMeal()
//        } catch (exc: Exception) {
//            Log.d("Recipes Repository", "getRandomMeal: $exc")
//            return Resource.Error("Unexpected Error occurred.")
//        }
//        return Resource.Success(response)
//    }

}