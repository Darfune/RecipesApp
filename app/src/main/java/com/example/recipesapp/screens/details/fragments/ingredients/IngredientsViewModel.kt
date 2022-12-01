package com.example.recipesapp.screens.details.fragments.ingredients

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipesapp.models.ingredients.shorter.SmallIngredient
import com.example.recipesapp.models.meals.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {
    fun setupData(myBundle: Meal): List<SmallIngredient>{
        val ingredientList = mutableListOf<SmallIngredient>()
        if (myBundle.strIngredient1 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient1,myBundle.strMeasure1))
        if (myBundle.strIngredient2 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient2,myBundle.strMeasure2))
        if (myBundle.strIngredient3 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient3,myBundle.strMeasure3))
        if (myBundle.strIngredient4 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient4,myBundle.strMeasure4))
        if (myBundle.strIngredient5 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient5,myBundle.strMeasure5))
        if (myBundle.strIngredient6 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient6,myBundle.strMeasure6))
        if (myBundle.strIngredient7 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient7,myBundle.strMeasure7))
        if (myBundle.strIngredient8 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient8,myBundle.strMeasure8))
        if (myBundle.strIngredient9 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient9,myBundle.strMeasure9))
        if (myBundle.strIngredient10 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient10,myBundle.strMeasure10))
        if (myBundle.strIngredient11 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient11,myBundle.strMeasure11))
        if (myBundle.strIngredient12 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient12,myBundle.strMeasure12))
        if (myBundle.strIngredient13 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient13,myBundle.strMeasure13))
        if (myBundle.strIngredient14 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient14,myBundle.strMeasure14))
        if (myBundle.strIngredient15 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient15,myBundle.strMeasure15))
        if (myBundle.strIngredient16 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient16,myBundle.strMeasure16))
        if (myBundle.strIngredient17 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient17,myBundle.strMeasure17))
        if (myBundle.strIngredient18 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient18,myBundle.strMeasure18))
        if (myBundle.strIngredient19 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient19,myBundle.strMeasure19))
        if (myBundle.strIngredient20 != "") ingredientList.add(SmallIngredient(myBundle.strIngredient20,myBundle.strMeasure20))

        return ingredientList
    }

}