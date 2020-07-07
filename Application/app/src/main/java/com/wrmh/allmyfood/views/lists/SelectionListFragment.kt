package com.wrmh.allmyfood.views.lists

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
import com.wrmh.allmyfood.adapters.ListRecyclerAdapter
import com.wrmh.allmyfood.adapters.RecipeRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentSelectionListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SelectionListFragment.newInstance] factory method to
 * create an Instance of this fragment.
 */
class SelectionListFragment : Fragment() {
    private lateinit var listAdapter: ListRecyclerAdapter
    private lateinit var viewModel: SelectionListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_shop_list)

        val binding = DataBindingUtil.inflate<FragmentSelectionListBinding>(
            inflater,
            R.layout.fragment_selection_list,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(SelectionListViewModel::class.java)
        listAdapter = ListRecyclerAdapter()

        viewModel.callback = {
            listAdapter = ListRecyclerAdapter()

            binding.recyclerViewLs.apply {
                layoutManager = LinearLayoutManager(container?.context)
                adapter = listAdapter
            }

            viewModel.response.value?.let { listAdapter.submitList(it) }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}