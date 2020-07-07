package com.wrmh.allmyfood.views.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.FragmentMylistBinding
import kotlinx.android.synthetic.main.fragment_mylist.*

/**
 * A simple [Fragment] subclass.
 */
class MyListFragment : Fragment() {

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

        return binding.root
    }

}
