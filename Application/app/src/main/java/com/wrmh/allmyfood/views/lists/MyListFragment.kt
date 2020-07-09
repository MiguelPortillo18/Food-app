package com.wrmh.allmyfood.views.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.MyListRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentMylistBinding
import com.wrmh.allmyfood.factory.ListViewModelFactory
import com.wrmh.allmyfood.models.ElementModel
import com.wrmh.allmyfood.models.ListModel

/**
 * A simple [Fragment] subclass.
 */
class MyListFragment : Fragment() {
    private lateinit var myListAdapter: MyListRecyclerAdapter
    private lateinit var binding: FragmentMylistBinding

    private val viewModel by lazy {
        AlternateViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_my_list)

        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mylist,
            container,
            false
        )

        val list = arguments?.get("list") as ListModel
        val deleteList = ArrayList<Boolean>()

        list.elements.forEach {
            deleteList.add(false)
        }

        val viewModelFactory = ListViewModelFactory(list, application)

        myListAdapter =
            MyListRecyclerAdapter(MyListRecyclerAdapter.OnChangeListener { element,
                                                                           pos,
                                                                           s ->
                list.elements[pos].desc = s.toString()
            },
                MyListRecyclerAdapter.OnClickListener { position, check ->
                    deleteList[position] = check
                }
            )

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyListViewModel::class.java)

        updateRecyclerView(list)

        binding.btnAddElement.setOnClickListener {
            val auxList = list.elements.toMutableList()

            if (auxList.size > 0 && auxList.last().desc.isEmpty()) {
                Toast.makeText(
                    application.applicationContext, "Rellena primero todos los elementos",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                auxList.add(
                    ElementModel(
                        "",
                        ""
                    )
                )
                deleteList.add(false)
            }

            list.elements = auxList.toList()

            updateRecyclerView(list)
        }

        binding.btnRmElement.setOnClickListener {
            val auxList = list.elements.toMutableList()

            for (i in 0 until auxList.size - 1) {
                if (deleteList[i]) {
                    deleteList.removeAt(i)
                    auxList.removeAt(i)
                }
            }

            list.elements = auxList.toList()

            updateRecyclerView(list)
        }

        binding.saveListApi.setOnClickListener {
            list.name = binding.listEditableName?.text.toString()

            if (list.name.isEmpty()) {
                Toast.makeText(
                    application.applicationContext, R.string.empty_field,
                    Toast.LENGTH_LONG
                ).show()

                binding.listEditableName?.requestFocus()
            } else {

                if (list._id == "UNCREATED_LIST")
                    viewModel.createUserList(list, application.applicationContext)
                else
                    viewModel.updateUserList(list, application.applicationContext)
            }
        }

        return binding.root
    }

    private fun updateRecyclerView(list: ListModel) {
        binding.recyclerViewMl.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = myListAdapter

            myListAdapter.submitList(list.elements)
        }
    }
}
