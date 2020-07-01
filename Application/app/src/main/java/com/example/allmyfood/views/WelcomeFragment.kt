package com.example.allmyfood.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.allmyfood.R
import com.example.allmyfood.databinding.FragmentWelcomeBinding
import com.example.allmyfood.models.CurrentUser

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_Allmyfood)

        val binding = DataBindingUtil.inflate<FragmentWelcomeBinding>(
            inflater,
            R.layout.fragment_welcome,
            container,
            false
        )

        binding.textView.text = "¡Bienvenido, ${CurrentUser.username}!"
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController()) ||
                super.onOptionsItemSelected(item)
    }
}
