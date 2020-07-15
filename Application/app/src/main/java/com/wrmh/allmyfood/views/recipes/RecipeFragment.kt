package com.wrmh.allmyfood.views.recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.IngredientsRecyclerAdapter
import com.wrmh.allmyfood.adapters.StepsRecyclerAdapter
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.databinding.FragmentRecipeBinding
import com.wrmh.allmyfood.factory.RecipesViewModelFactory
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : Fragment() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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

        binding.viewModel = ViewModelProviders.of(this@RecipeFragment, viewModelFactory)
            .get(DetailRecipeViewModel::class.java)

        binding.apply {

            recyclerViewIngredients.apply {
                layoutManager = LinearLayoutManager(container?.context)
                adapter = ingredientAdapter

                recipe.ingredients?.let { ingredientAdapter.submitList(it) }
            }

            recyclerViewSteps.apply {
                layoutManager = LinearLayoutManager(container?.context)
                adapter = stepsAdapter

                recipe.steps?.let { stepsAdapter.submitList(it) }
            }

            recipeDetailDesc.text = recipe.desc
        }

        if(CurrentUser.previousFragmentForRecipe == "exp"){
            binding.btnDeleteRecipePublic.visibility = View.INVISIBLE
        }

        binding.btnDeleteRecipePublic.setOnClickListener {
            deleteRecipe(recipe._id!!, this@RecipeFragment.context!!)
        }

        Picasso.get().load(recipe.recipeImage).into(binding.recipeDetailImage)

        return binding.root
    }

    private fun deleteRecipe(_id: String, context: Context) {
        coroutineScope.launch {
            val deleteRecipeDeferred = API().deleteRecipeAsync(_id)

            try {
                val apiResponse = deleteRecipeDeferred.await()

                if (apiResponse.error)
                    throw java.lang.Exception()

                Toast.makeText(context, "Receta eliminada ...", Toast.LENGTH_LONG).show()
                this@RecipeFragment.findNavController().popBackStack()

            } catch (e: Exception) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }
}
