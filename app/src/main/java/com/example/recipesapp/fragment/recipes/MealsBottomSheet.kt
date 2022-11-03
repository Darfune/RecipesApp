package com.example.recipesapp.fragment.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipesapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MealsBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.meals_bottom_sheet, container, false)
    }

}