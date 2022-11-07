package com.example.recipesapp.fragment.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.recipesapp.R
import com.example.recipesapp.databinding.MealsBottomSheetBinding
import com.example.recipesapp.utils.Constants.Companion.DEFAULT_MEAL_CATEGORY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class MealsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: MealsBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealsViewModel: MealsViewModel

    private var mealCategoryChip = DEFAULT_MEAL_CATEGORY
    private var mealCategoryIdChip = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsViewModel = ViewModelProvider(requireActivity())[MealsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = MealsBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mealsViewModel = mealsViewModel

        mealsViewModel.readMealCategory.asLiveData().observe(viewLifecycleOwner) { value ->
            mealCategoryChip = value.selectedMealCategory
            updateChip(value.selectedMealCategoryId, binding.mealTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId[0])
            val selectedMealCategory = chip.text.toString().lowercase(Locale.ROOT)
            mealCategoryChip = selectedMealCategory
            mealCategoryIdChip = selectedChipId[0]
        }

        binding.applyMealTypeButton.setOnClickListener {
            mealsViewModel.saveMealCategory(
                mealCategoryChip,
                mealCategoryIdChip
            )
            val action = MealsBottomSheetDirections
                .actionMealsBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(selectedMealCategoryId: Int, mealTypeChipGroup: ChipGroup) {
        if (mealCategoryIdChip != 0) {
            try {
                mealTypeChipGroup.findViewById<Chip>(selectedMealCategoryId).isChecked = true
            } catch (exc: Exception) {
                Log.d("MealsBottomSheet", "updateChip: ${exc.message.toString()}")
            }
        }
    }

}