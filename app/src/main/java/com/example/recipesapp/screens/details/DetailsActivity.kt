package com.example.recipesapp.screens.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.recipesapp.R
import com.example.recipesapp.adapters.PagerAdapter
import com.example.recipesapp.database.entities.FavoritesEntity
import com.example.recipesapp.databinding.ActivityDetailsBinding
import com.example.recipesapp.screens.details.fragments.ingredients.IngredientsFragment
import com.example.recipesapp.screens.details.fragments.instructions.InstructionsFragment
import com.example.recipesapp.screens.details.fragments.overview.OverviewFragment
import com.example.recipesapp.screens.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var mealSaved = false
    private var savedMealId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("mealBundle", args.meal)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkIfMealIsSaved(menuItem!!)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if (item.itemId == R.id.save_to_favorites_menu && !mealSaved) saveToFavorites(item)
        else if (item.itemId == R.id.save_to_favorites_menu && mealSaved) removeFromFavorites(item)
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0,
            args.meal
        )
        mainViewModel.insertFavoriteMeal(favoritesEntity)
        changeMenuItemColor(item, R.color.favoritesStarColor)
        showSnackBar("Meal saved")
        mealSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            savedMealId,
            args.meal
        )
        Log.d("removeFromFavorites: ", "$savedMealId ${args.meal}")
        mainViewModel.deleteFavoriteMeal(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites")
        mealSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun checkIfMealIsSaved(menuItem: MenuItem) {
        mainViewModel.readFavoriteMeals.observe(this) { favoritesEntity ->
            try {
                for (savedMeal in favoritesEntity){

                    Log.d("checkIfMealIsSaved: ", "${savedMeal.meal.idMeal} - ${args.meal.idMeal}")
                    if (savedMeal.meal.idMeal == args.meal.idMeal){
                        changeMenuItemColor(menuItem, R.color.favoritesStarColor)
                        savedMealId = args.meal.idMeal!!.toInt()
                        mealSaved = true
                        break
                    } else changeMenuItemColor(menuItem, R.color.white)
                }
            } catch (exc: Exception) {
                Log.d("checkIfMealIsSaved: ", exc.message.toString())
            }
        }
    }
}