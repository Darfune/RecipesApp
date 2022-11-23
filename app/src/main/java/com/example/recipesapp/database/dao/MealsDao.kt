package com.example.recipesapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipesapp.database.entities.FavoritesEntity
import com.example.recipesapp.database.entities.MealsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {

    // Add meal to datastore
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(mealsEntity: MealsEntity)

    // Read meals from database
    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readMeals(): Flow<List<MealsEntity>>

    // Add meal to favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMeal(favoritesEntity: FavoritesEntity)

    // Read all meals from favorites
    @Query("SELECT * FROM favorites_meals_table ORDER BY id ASC")
    fun readFavoriteMeal(): Flow<List<FavoritesEntity>>

    // Delete specific meal from favorites
    @Delete
    suspend fun deleteFavoriteMeal(favoritesEntity: FavoritesEntity)

    // Delete all favorites
    @Query("DELETE FROM favorites_meals_table")
    suspend fun deleteAllFavoriteMeals()
}