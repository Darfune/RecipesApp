package com.example.recipesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.MealsRowLayoutBinding
import com.example.recipesapp.models.meals.ListOfMeals
import com.example.recipesapp.models.meals.Meal
import com.example.recipesapp.utils.MealsDiffUtils

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {

    private var meals = emptyList<Meal>()

    class MealsViewHolder(private val binding: MealsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meals: Meal) {
            binding.mealsList = meals
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MealsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealsRowLayoutBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return MealsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun setData(newData: ListOfMeals) {
        val mealsDiffUtil = MealsDiffUtils(meals, newData.meals)
        val diffUtilResult = DiffUtil.calculateDiff(mealsDiffUtil)
        meals = newData.meals
        diffUtilResult.dispatchUpdatesTo(this)
    }

}