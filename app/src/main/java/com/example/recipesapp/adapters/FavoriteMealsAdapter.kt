package com.example.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.database.entities.FavoritesEntity
import com.example.recipesapp.databinding.FavoritesRowLayoutBinding
import com.example.recipesapp.utils.MealsDiffUtils

class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealsViewHolder>() {

    private var favoriteMeals = emptyList<FavoritesEntity>()

    class FavoriteMealsViewHolder(private val binding: FavoritesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteMealsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoritesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return FavoriteMealsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealsViewHolder =
        FavoriteMealsViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) =
        holder.bind(favoriteMeals[position])

    override fun getItemCount(): Int = favoriteMeals.size

    fun setData(newFavoriteMeals: List<FavoritesEntity>) {
        val favoriteMealsDiffUtil = MealsDiffUtils(favoriteMeals, newFavoriteMeals)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteMealsDiffUtil)
        favoriteMeals = newFavoriteMeals
        diffUtilResult.dispatchUpdatesTo(this)
    }
}