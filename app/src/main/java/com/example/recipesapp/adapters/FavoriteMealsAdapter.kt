package com.example.recipesapp.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.database.entities.FavoritesEntity
import com.example.recipesapp.databinding.FavoritesRowLayoutBinding
import com.example.recipesapp.screens.main.fragments.favorites.FavoriteRecipesFragmentDirections
import com.example.recipesapp.utils.MealsDiffUtils

class FavoriteMealsAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealsViewHolder>(),
    ActionMode.Callback {

    private var multiSelection = false
    private var selectMeals = arrayListOf<FavoritesEntity>()
    private var favoritesViewHolder = arrayListOf<FavoriteMealsViewHolder>()
    private var favoriteMeals = emptyList<FavoritesEntity>()

    class FavoriteMealsViewHolder(val binding: FavoritesRowLayoutBinding) :
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

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        holder.bind(favoriteMeals[position])
        favoritesViewHolder.add(holder)


        /**
         * Single Click Listener
         */
        holder.binding.favoriteMealssRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, favoriteMeals[position])
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        favoriteMeals[position].meal
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long Click Listener
         */
        holder.binding.favoriteMealssRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, favoriteMeals[position])
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    private fun applySelection(holder: FavoriteMealsViewHolder, currentMeal: FavoritesEntity) {
        if (selectMeals.contains(currentMeal)) {
            selectMeals.remove(currentMeal)
            changeMealStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        } else {
            selectMeals.add(currentMeal)
            changeMealStyle(holder, R.color.cardBackgroundLightColor, R.color.cardSelectedStroke)
        }
    }

    private fun changeMealStyle(
        holder: FavoriteMealsViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.binding.favoriteMealssRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.favoritesRowCardView.strokeColor = ContextCompat.getColor(
            requireActivity,
            strokeColor
        )
    }

    override fun getItemCount(): Int = favoriteMeals.size

    /**
     * For Delete menu
     */

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        favoritesViewHolder.forEach { holder ->
            changeMealStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectMeals.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoriteMeals: List<FavoritesEntity>) {
        val favoriteMealsDiffUtil = MealsDiffUtils(favoriteMeals, newFavoriteMeals)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteMealsDiffUtil)
        favoriteMeals = newFavoriteMeals
        diffUtilResult.dispatchUpdatesTo(this)
    }

}