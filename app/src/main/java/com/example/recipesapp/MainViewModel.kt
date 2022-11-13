package com.example.recipesapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.recipesapp.data.Resource
import com.example.recipesapp.database.MealsDatabase
import com.example.recipesapp.database.dao.MealsDao
import com.example.recipesapp.database.entities.MealsEntity
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.models.meals.Meal
import com.example.recipesapp.models.mealssmall.MealsSmallList
import com.example.recipesapp.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val mealsDao: MealsDao,
    application: Application
) : AndroidViewModel(application) {

    // Room Database
    val readMeals: LiveData<List<MealsEntity>> = mealsDao.readMeals().asLiveData()

    private fun insertMeals(mealsEntity: MealsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            mealsDao.insertMeals(mealsEntity)
        }

    // Retrofit API
    var mealsResponse: MutableLiveData<Resource<ListOfMeals>> = MutableLiveData()
    var searchedMealsResponse: MutableLiveData<Resource<ListOfMeals>> = MutableLiveData()
    //    var mealsByCategoryResponse: MutableLiveData<Resource<MealsSmallList>> = MutableLiveData()


    var isLoading: Boolean by mutableStateOf(true)

    // Default search for RecipesFragment
    fun getMeals() = viewModelScope.launch {
        if (hasInternetConnection()) getMealsSafeCall("")
        else mealsResponse.value = Resource.Error("No Internet Connection")
    }

    // Search call in RecipesFragment
    fun searchMeals(searchQuery: String) = viewModelScope.launch {
        if (hasInternetConnection()) getMealsSafeCall(searchQuery)
        else searchedMealsResponse.value = Resource.Error("No Internet Connection")
    }

    private suspend fun getMealsSafeCall(searchQuery: String) {
        try {
            when (val result = recipesRepository.getMeals(searchQuery)) {
                is Resource.Success -> {
                    if (searchQuery.isBlank()) {
                        mealsResponse.value = result
                        Log.d("MainViewModel", "getMeals: ${mealsResponse.value}")
                        isLoading = false
                        val mealRecipe = mealsResponse.value!!.data
                        if (mealRecipe != null) {
                            offlineCacheMealRecipes(mealRecipe)
                        }
                    } else {
                        searchedMealsResponse.value = result
                        Log.d("MainViewModel", "getMeals: ${searchedMealsResponse.value}")
                        isLoading = false
                    }
                }
                is Resource.Error -> {
                    Log.e("MainViewModel", "getMeals: Failed to get response")
                    isLoading = false
                }
                else -> isLoading = false
            }
        } catch (exception: Exception) {
            Log.d("MainViewModel", "getMeals: ${exception.message.toString()}")
        }
    }

    /*
    fun getMealsByCategory(category: String) = viewModelScope.launch {
        if (hasInternetConnection()) {
            try {
                when (val result = recipesRepository.getMealsByCategory(category)) {
                    is Resource.Success -> {
                        mealsByCategoryResponse.value = result
                        Log.d("MainViewModel", "getMealsByCategory: ${mealsResponse.value}")
                        isLoading = false
//                        val mealsCategory = mealsByCategoryResponse.value!!.data
//                        if (mealsByCategoryResponse != null) {
//                            offlineCacheMealRecipes(mealsByCategoryResponse)
//                        }
                    }
                    is Resource.Error -> {
                        Log.e("MainViewModel", "mealsByCategoryResponse: Failed to get response")
                        isLoading = false
                    }
                    else -> isLoading = false
                }
            } catch (exception: Exception) {
                Log.d("MainViewModel", "mealsByCategoryResponse: ${exception.message.toString()}")
            }
        } else mealsResponse.value = Resource.Error("No Internet Connection")
    }
    */

    // Insert data to database
    private fun offlineCacheMealRecipes(mealRecipe: ListOfMeals) =
        insertMeals(MealsEntity(mealRecipe))

    // Internet permission
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activityNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activityNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}