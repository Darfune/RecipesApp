package com.example.recipesapp.bindingadaptors

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.recipesapp.R

class MealsRowBinding {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(500)
                error(R.drawable.ic_meal_placeholder)
            }
        }

    }

}