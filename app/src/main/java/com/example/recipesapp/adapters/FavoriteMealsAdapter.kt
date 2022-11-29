package com.example.recipesapp.adapters

import android.view.*
import androidx.constraintlayout.widget.ConstraintSet.Layout
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

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        holder.bind(favoriteMeals[position])


        /**
         * Single Click Listener
         */
        holder.itemView.findViewById<View>(R.id.favoriteMealssRowLayout).setOnClickListener {
            val action =
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                    favoriteMeals[position].meal
                )
            holder.itemView.findNavController().navigate(action)
        }

        /**
         * Long Click Listener
         */
        holder.itemView.findViewById<View>(R.id.favoriteMealssRowLayout).setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int = favoriteMeals.size

    fun setData(newFavoriteMeals: List<FavoritesEntity>) {
        val favoriteMealsDiffUtil = MealsDiffUtils(favoriteMeals, newFavoriteMeals)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteMealsDiffUtil)
        favoriteMeals = newFavoriteMeals
        diffUtilResult.dispatchUpdatesTo(this)
    }

    /**
     * For Delete menu
     */

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {

    }
}