package com.example.allmyfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.allmyfood.databinding.FragmentExploreBinding

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_explore)

       val binding = DataBindingUtil.inflate<FragmentExploreBinding>(inflater, R.layout.fragment_explore,container,false)

        binding.btnExtendRecipe.setOnClickListener{
            it.findNavController().navigate(R.id.action_exploreFragment_to_recipeFragment)
        }

        return binding.root
    }
}
