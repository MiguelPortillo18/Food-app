package com.wrmh.allmyfood.views.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.MyListRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentMylistBinding
import com.wrmh.allmyfood.factory.ListViewModelFactory
import com.wrmh.allmyfood.models.ListModel

/**
 * A simple [Fragment] subclass.
 */
class MyListFragment : Fragment() {
    private lateinit var myListAdapter: MyListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_my_list)

        val application = requireNotNull(activity).application
        val binding = DataBindingUtil.inflate<FragmentMylistBinding>(
            inflater,
            R.layout.fragment_mylist,
            container,
            false
        )

        val list = arguments?.get("list") as ListModel
        val viewModelFactory = ListViewModelFactory(list, application)
        myListAdapter = MyListRecyclerAdapter()

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyListViewModel::class.java)

        binding.recyclerViewMl.apply {
            layoutManager = LinearLayoutManager(container?.context)
            myListAdapter.submitList(list.elements)
            adapter = myListAdapter
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
