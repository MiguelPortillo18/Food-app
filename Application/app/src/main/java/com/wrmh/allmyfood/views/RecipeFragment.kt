package com.wrmh.allmyfood.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.FragmentRecipeBinding
import com.wrmh.allmyfood.models.RecipeModel

/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment(openRecipe: RecipeModel?) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_recipe)

        val binding = DataBindingUtil.inflate<FragmentRecipeBinding>(
            inflater,
            R.layout.fragment_recipe, container, false
        )

        binding.btnReturnRecipe.setOnClickListener {
            it.findNavController().navigate(R.id.action_recipeFragment_to_exploreFragment)
        }

        return binding.root
    }
}
