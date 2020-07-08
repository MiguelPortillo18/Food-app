package com.wrmh.allmyfood.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.FragmentRecipeBinding
import com.wrmh.allmyfood.factory.RecipesViewModelFactory
import com.wrmh.allmyfood.models.RecipeModel
import com.wrmh.allmyfood.views.recipes.DetailRecipeViewModel
import com.wrmh.allmyfood.views.recipes.RecipesViewModel

/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_recipe)

        val application = requireNotNull(activity).application
        val binding = DataBindingUtil.inflate<FragmentRecipeBinding>(
            inflater,
            R.layout.fragment_recipe, container, false
        )

        val recipe = arguments?.get("recipe") as RecipeModel
        val viewModelFactory = RecipesViewModelFactory(recipe, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailRecipeViewModel::class.java)

        binding.btnReturnRecipe.setOnClickListener {
            it.findNavController().navigate(R.id.action_recipeFragment_to_exploreFragment)
        }

        binding.recipeDetailDesc?.text = recipe.desc

        Picasso.get().load(recipe.recipeImage).into(binding.recipeDetailImage)

        return binding.root
    }
}
