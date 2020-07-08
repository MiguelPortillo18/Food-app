package com.wrmh.allmyfood.views.lists

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
import com.wrmh.allmyfood.adapters.SelectionListRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentSelectionListBinding
import com.wrmh.allmyfood.models.ListModel

/**
 * A simple [Fragment] subclass.
 * Use the [SelectionListFragment.newInstance] factory method to
 * create an Instance of this fragment.
 */
class SelectionListFragment : Fragment() {
    private lateinit var selectionListAdapter: SelectionListRecyclerAdapter
    private lateinit var viewModel: SelectionListViewModel
    private lateinit var binding: FragmentSelectionListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_shop_list)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_selection_list,
            container,
            false
        )

        selectionListAdapter = SelectionListRecyclerAdapter(SelectionListRecyclerAdapter
            .OnClickListener {
                viewModel.displayListDetail(it)
            })

        viewModel = ViewModelProvider(this).get(SelectionListViewModel::class.java)

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val bundle = bundleOf("list" to it)

                this.findNavController()
                    .navigate(R.id.action_selectionListFragment_to_myListFragment, bundle)

                viewModel.displayListDetailCompleted()
            }
        })

        binding.btnAddList?.setOnClickListener {
            val list = ListModel("UNCREATED_LIST", "", "", ArrayList())

            val bundle = bundleOf("list" to list)

            this.findNavController().navigate(
                R.id.action_selectionListFragment_to_myListFragment,
                bundle
            )
        }

        viewModel.callback = {
            updateRecyclerView()
        }

        return binding.root
    }

    private fun updateRecyclerView() {
        binding.recyclerViewLs.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = selectionListAdapter

            viewModel.response.value?.let { selectionListAdapter.submitList(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }
}