package com.wrmh.allmyfood.views.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.RecipeRecyclerAdapter
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.databinding.FragmentExploreBinding
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        initRecyclerView(container)
        recipeAdapter.submitList(viewModel.response.value!!)

        return binding.root
    }

    private fun initRecyclerView(container: ViewGroup?) {
        recycler_view_ex?.apply {
            layoutManager = LinearLayoutManager(container?.context)
            adapter = recipeAdapter
        }
    }
}
