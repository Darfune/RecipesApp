package com.example.recipesapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipesapp.database.entities.MealsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(mealsEntity: MealsEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readMeals(): Flow<List<MealsEntity>>
}