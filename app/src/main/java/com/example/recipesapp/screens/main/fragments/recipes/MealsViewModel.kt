package com.example.recipesapp.screens.main.fragments.recipes

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.repository.DataStoreRepository
import com.example.recipesapp.utils.Constants.Companion.DEFAULT_MEAL_CATEGORY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealCategory = DEFAULT_MEAL_CATEGORY

    var networkStatus = false
    var backOnline = false

    val readMealCategory = dataStoreRepository.readMealCategory
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    // Search Area
    fun applySearchQuery(searchQuery: String): String = searchQuery


    // Categories in Menu
    fun saveMealCategory(mealCategory: String, mealCategoryId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealCategory(mealCategory, mealCategoryId)
        }

    fun searchByCategory(): String {
        viewModelScope.launch {
            readMealCategory.collect { category ->
                mealCategory = category.selectedMealCategory
            }
        }
        return mealCategory
    }



    // Network Status

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveBackOnline(backOnline)
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus && backOnline) {
            Toast.makeText(getApplication(), "Back Online.", Toast.LENGTH_SHORT).show()
            saveBackOnline(false)
        }
    }

}