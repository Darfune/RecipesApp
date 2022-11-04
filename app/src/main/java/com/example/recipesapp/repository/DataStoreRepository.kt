package com.example.recipesapp.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.recipesapp.utils.Constants.Companion.PREFERENCES_MEAL_CATEGORY
import com.example.recipesapp.utils.Constants.Companion.PREFERENCES_MEAL_CATEGORY_ID
import com.example.recipesapp.utils.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealCategory = preferencesKey<String>(PREFERENCES_MEAL_CATEGORY)
        val selectedMealCategoryId = preferencesKey<Int>(PREFERENCES_MEAL_CATEGORY_ID)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveMealCategory(mealCategory: String, mealCategoryId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealCategory] = mealCategory
            preferences[PreferenceKeys.selectedMealCategoryId] = mealCategoryId
        }
    }

    val readMealCategory: Flow<MealCategory> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealCategory = preferences[PreferenceKeys.selectedMealCategory] ?: "beef"
            val selectedMealCategoryId = preferences[PreferenceKeys.selectedMealCategoryId] ?: 0
            MealCategory(
                selectedMealCategory,
                selectedMealCategoryId
            )
        }
}

data class MealCategory(
    val selectedMealCategory: String,
    val selectedMealCategoryId: Int,
)