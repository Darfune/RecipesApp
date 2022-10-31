package com.example.recipesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesapp.database.dao.MealsDao
import com.example.recipesapp.database.entities.MealsEntity
import com.example.recipesapp.database.typeconverters.MealsTypeConverter

@Database(
    entities = [MealsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MealsTypeConverter::class)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealsDao(): MealsDao
}