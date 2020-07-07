package com.wrmh.allmyfood.views.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.RecipeRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentMyRecipesBinding
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_my_recipes.*

/**
 * A simple [Fragment] subclass.
 */
class MyRecipesFragment : Fragment() {
    private lateinit var recipeAdapter: RecipeRecyclerAdapter
    private lateinit var viewModel: MyRecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_my_recipe)

        val binding = DataBindingUtil.inflate<FragmentMyRecipesBinding>(
            inflater,
            R.layout.fragment_my_recipes,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(MyRecipesViewModel::class.java)
        recipeAdapter = RecipeRecyclerAdapter()

        viewModel.callbackFunction = {
            recipeAdapter = RecipeRecyclerAdapter()

            binding.recyclerViewMy.apply {
                layoutManager = LinearLayoutManager(container?.context)
                adapter = recipeAdapter
            }

            viewModel.response.value?.let { recipeAdapter.submitList(it) }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
