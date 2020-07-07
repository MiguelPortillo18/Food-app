package com.wrmh.allmyfood.views.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.RecipeRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentExploreBinding
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.android.synthetic.main.fragment_explore.*

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {
    private lateinit var recipeAdapter: RecipeRecyclerAdapter
    private lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_explore)

        val binding = DataBindingUtil.inflate<FragmentExploreBinding>(
            inflater,
            R.layout.fragment_explore,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        recipeAdapter = RecipeRecyclerAdapter()

        viewModel.callbackFunction = {
            recipeAdapter = RecipeRecyclerAdapter()

            binding.recyclerViewEx.apply {
                layoutManager = LinearLayoutManager(container?.context)
                adapter = recipeAdapter
            }

            viewModel.response.value?.let { recipeAdapter.submitList(it) }
        }

        return binding.root
    }
}
