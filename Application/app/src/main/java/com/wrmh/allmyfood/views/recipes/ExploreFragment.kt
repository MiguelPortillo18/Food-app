package com.wrmh.allmyfood.views.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.RecipeRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentExploreBinding

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {
    private lateinit var recipeAdapter: RecipeRecyclerAdapter
    private lateinit var viewModel: RecipesViewModel
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_explore)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_explore,
            container,
            false
        )

        recipeAdapter = RecipeRecyclerAdapter(
            RecipeRecyclerAdapter.OnClickListener{
                viewModel.displayRecipeDetail(it)
            }
        )

        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        viewModel.redirection(true)

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val bundle = bundleOf("recipe" to it)

                this.findNavController()
                    .navigate(R.id.action_exploreFragment_to_recipeFragment, bundle)

                viewModel.displayRecipeDetailCompleted()
            }
        })

        viewModel.callbackFunction = {
            updateRecyclerView()
        }

        return binding.root
    }

    private fun updateRecyclerView(){
        binding.recyclerViewEx.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = recipeAdapter

            viewModel.response.value?.let { recipeAdapter.submitList(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }
}
