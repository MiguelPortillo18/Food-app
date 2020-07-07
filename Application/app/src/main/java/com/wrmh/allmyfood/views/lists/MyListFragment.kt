package com.wrmh.allmyfood.views.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.MyListRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentMylistBinding
import com.wrmh.allmyfood.models.ListModel

/**
 * A simple [Fragment] subclass.
 */
class MyListFragment(openList: ListModel?) : Fragment() {
    private lateinit var myListAdapter: MyListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_my_list)

        val binding = DataBindingUtil.inflate<FragmentMylistBinding>(
            inflater,
            R.layout.fragment_mylist,
            container,
            false
        )

        myListAdapter = MyListRecyclerAdapter()

        binding.recyclerViewMl.apply {
            layoutManager = LinearLayoutManager(container?.context)
            adapter = myListAdapter
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mylist, container, false)
    }

}
