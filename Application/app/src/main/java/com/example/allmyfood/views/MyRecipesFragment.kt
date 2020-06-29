package com.example.allmyfood.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.allmyfood.R
import com.example.allmyfood.databinding.FragmentCreateRecipeBinding
import com.example.allmyfood.databinding.FragmentMyRecipesBinding

/**
 * A simple [Fragment] subclass.
 */
class MyRecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_my_recipe)

        val binding = DataBindingUtil.inflate<FragmentMyRecipesBinding>(
            inflater,
            R.layout.fragment_my_recipes,
            container,
            false
        )

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_recipes, container, false)
    }

}
