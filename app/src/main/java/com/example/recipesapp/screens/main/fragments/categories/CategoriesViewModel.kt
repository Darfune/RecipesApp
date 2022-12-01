package com.example.recipesapp.screens.main.fragments.categories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.Resource
import com.example.recipesapp.models.areas.Areas
import com.example.recipesapp.models.categories.Categories
import com.example.recipesapp.models.meals.shorter.ShortMealList
import com.example.recipesapp.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    application: Application
) : AndroidViewModel(application) {

    var isLoading: Boolean by mutableStateOf(true)

    var categoriesResponse: MutableLiveData<Resource<Categories>> = MutableLiveData()
    var areasResponse: MutableLiveData<Resource<Areas>> = MutableLiveData()
    var mealsByCategoryResponse: MutableLiveData<Resource<ShortMealList>> = MutableLiveData()
    var mealsByAreaResponse: MutableLiveData<Resource<ShortMealList>> = MutableLiveData()

    fun getCategories() = viewModelScope.launch {
        if (hasInternetConnection()) getCategoriesSafeCall()
        else categoriesResponse.value = Resource.Error("No Internet Connection")
    }

    fun getAreas() = viewModelScope.launch {
        if (hasInternetConnection()) getAreasSafeCall()
        else areasResponse.value = Resource.Error("No Internet Connection")
    }

    fun getMealsByCategory(category: String) = viewModelScope.launch {
        if (hasInternetConnection()) getMealsByCategorySafeCall(category)
        else mealsByCategoryResponse.value = Resource.Error("No Internet Connection")
    }

    fun getMealsByArea(area: String) = viewModelScope.launch {
        if (hasInternetConnection()) getMealsByAreaSafeCall(area)
        else mealsByAreaResponse.value = Resource.Error("No Internet Connection")
    }


    private suspend fun getCategoriesSafeCall() {
        categoriesResponse.value = Resource.Loading()
        try {
            when (val result = recipesRepository.getCategories()) {
                is Resource.Success -> {
                    categoriesResponse.value = result
                    Log.d(
                        "CategoriesViewModel",
                        "getCategoriesSafeCall: ${categoriesResponse.value}"
                    )
                    isLoading = false
                }
                is Resource.Error -> {
                    Log.e("CategoriesViewModel", "getCategoriesSafeCall: Failed to get response")
                    isLoading = false
                }
                else -> isLoading = false
            }
        } catch (ex: Exception) {
            Log.d("CategoriesViewModel", "getCategoriesSafeCall: ${ex.message.toString()}")
        }
    }

    private suspend fun getAreasSafeCall() {
        areasResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                when (val result = recipesRepository.getAreas()) {
                    is Resource.Success -> {
                        areasResponse.value = result
                        Log.d("CategoriesViewModel", "getAreasSafeCall: ${areasResponse.value}")
                        isLoading = false
                    }
                    is Resource.Error -> {
                        Log.e("CategoriesViewModel", "getAreasSafeCall: Failed to get response")
                        isLoading = false
                    }
                    else -> isLoading = false
                }
            } catch (ex: Exception) {
                Log.d("CategoriesViewModel", "getAreasSafeCall: ${ex.message.toString()}")
            }
        }
    }

    private suspend fun getMealsByCategorySafeCall(category: String) {
        mealsByCategoryResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                when (val result = recipesRepository.getMealsByCategory(category)) {
                    is Resource.Success -> {
                        mealsByCategoryResponse.value = result
                        Log.d(
                            "CategoriesViewModel",
                            "getMealsByCategorySafeCall: ${mealsByCategoryResponse.value}"
                        )
                        isLoading = false
                    }
                    is Resource.Error -> {
                        Log.e(
                            "CategoriesViewModel",
                            "getMealsByCategorySafeCall: Failed to get response"
                        )
                        isLoading = false
                    }
                    else -> isLoading = false
                }
            } catch (ex: Exception) {
                Log.d("CategoriesViewModel", "getMealsByCategorySafeCall: ${ex.message.toString()}")
            }
        }
    }

    private suspend fun getMealsByAreaSafeCall(area: String) {
        mealsByAreaResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                when (val result = recipesRepository.getMealsByArea(area)) {
                    is Resource.Success -> {
                        mealsByAreaResponse.value = result
                        Log.d(
                            "CategoriesViewModel",
                            "getMealsByAreaSafeCall: ${mealsByAreaResponse.value}"
                        )
                        isLoading = false
                    }
                    is Resource.Error -> {
                        Log.e(
                            "CategoriesViewModel",
                            "getMealsByAreaSafeCall: Failed to get response"
                        )
                        isLoading = false
                    }
                    else -> isLoading = false
                }
            } catch (ex: Exception) {
                Log.d("CategoriesViewModel", "getMealsByAreaSafeCall: ${ex.message.toString()}")
            }
        }
    }

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