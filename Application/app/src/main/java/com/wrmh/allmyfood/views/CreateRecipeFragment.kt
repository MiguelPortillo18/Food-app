package com.wrmh.allmyfood.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.FragmentCreateRecipeBinding

/**
 * A simple [Fragment] subclass.
 */
class CreateRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_create_recipe)

        val binding = DataBindingUtil.inflate<FragmentCreateRecipeBinding>(
            inflater,
            R.layout.fragment_create_recipe,
            container,
            false
        )

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_recipe, container, false)
    }
}
