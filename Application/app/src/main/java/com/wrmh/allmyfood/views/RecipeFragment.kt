package com.wrmh.allmyfood.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.IngredientsRecyclerAdapter
import com.wrmh.allmyfood.adapters.StepsRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentRecipeBinding
import com.wrmh.allmyfood.factory.RecipesViewModelFactory
import com.wrmh.allmyfood.models.RecipeModel
import com.wrmh.allmyfood.views.recipes.DetailRecipeViewModel

/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : Fragment() {

    private val ingredientAdapter by lazy {
        IngredientsRecyclerAdapter()
    }

    private val stepsAdapter by lazy {
        StepsRecyclerAdapter()
    }

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

        binding.recyclerViewIngredients?.apply {
            layoutManager = LinearLayoutManager(container?.context)
            adapter = ingredientAdapter

            recipe.ingredients?.let { ingredientAdapter.submitList(it) }
        }

        binding.recyclerViewSteps?.apply {
            layoutManager = LinearLayoutManager(container?.context)
            adapter = stepsAdapter

            recipe.steps?.let { stepsAdapter.submitList(it) }
        }

        binding.recipeDetailDesc?.text = recipe.desc

        Picasso.get().load(recipe.recipeImage).into(binding.recipeDetailImage)

        return binding.root
    }
}
