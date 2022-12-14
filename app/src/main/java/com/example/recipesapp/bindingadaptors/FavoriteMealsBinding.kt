package com.example.recipesapp.bindingadaptors

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.adapters.FavoriteMealsAdapter
import com.example.recipesapp.database.entities.FavoritesEntity

class FavoriteMealsBinding {

    companion object {

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntityList: List<FavoritesEntity>?,
            favoriteMealsAdapter: FavoriteMealsAdapter?
        ) {
            if (favoritesEntityList.isNullOrEmpty()) { // If the are not any favorites
                when (view) {
                    is ImageView -> view.visibility = View.VISIBLE
                    is TextView -> view.visibility = View.VISIBLE
                    is RecyclerView -> view.visibility = View.INVISIBLE
                }
            } else {
                when (view) {
                    is ImageView -> view.visibility = View.INVISIBLE
                    is TextView -> view.visibility = View.INVISIBLE
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        favoriteMealsAdapter?.setData(favoritesEntityList)
                    }
                }
            }
        }
    }
}
