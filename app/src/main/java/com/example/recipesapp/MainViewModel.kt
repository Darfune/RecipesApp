package com.example.recipesapp

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
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    application: Application
) : AndroidViewModel(application) {

    private var mealsResponse: MutableLiveData<Resource<ListOfMeals>> = MutableLiveData()
    private var isLoading: Boolean by mutableStateOf(true)

    fun getMeals() = viewModelScope.launch {
        if (hasInternetConnection()) {
            try {
                when (val result = recipesRepository.getMeals()) {
                    is Resource.Success -> {
                        mealsResponse.value = result
                        isLoading = false
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
        } else mealsResponse.value = Resource.Error("No Internet Connection")
    }

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