package com.example.recipesapp.bindingadaptors

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class MealsRowBinding {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(500)
            }
        }

    }

}