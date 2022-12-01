package com.example.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.databinding.IngredientsRowLayoutBinding
import com.example.recipesapp.models.ingredients.shorter.SmallIngredient
import com.example.recipesapp.utils.Constants.Companion.BASE_IMG_URL
import com.example.recipesapp.utils.Constants.Companion.ING_IMG_URL
import com.example.recipesapp.utils.Constants.Companion.END_IMG_URL
import com.example.recipesapp.utils.MealsDiffUtils

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<SmallIngredient>()

    class IngredientsViewHolder(val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder =
        IngredientsViewHolder(IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.binding.ingredientImageView.load(BASE_IMG_URL + ING_IMG_URL + ingredientsList[position].strIngredientName + END_IMG_URL) {
            crossfade(600)
            error(R.drawable.ic_meal_placeholder)
        }
        holder.binding.ingredientNameTextView.text = ingredientsList[position].strIngredientName
        holder.binding.ingredientMeasurementTextView.text = ingredientsList[position].strMeasure
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredientsList: List<SmallIngredient>){
        val ingredientDiffUtil = MealsDiffUtils(ingredientsList, newIngredientsList)
        val diffUtilIngredient = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredientsList = newIngredientsList
        diffUtilIngredient.dispatchUpdatesTo(this)
    }
}