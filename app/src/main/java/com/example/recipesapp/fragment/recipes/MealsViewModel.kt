package com.example.recipesapp.fragment.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    val readMealCategory = dataStoreRepository.readMealCategory

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

}