package com.wrmh.allmyfood.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.FragmentWelcomeBinding
import com.wrmh.allmyfood.models.CurrentUser

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

        val sharedPref = activity?.getSharedPreferences("USER_LOG", Context.MODE_PRIVATE)

        binding.apply {
            textView.text = "¡Bienvenido, ${CurrentUser.fullname!!.split(" ")[0]}!"
            onLoggingOut?.setOnClickListener{

                val editor = sharedPref?.edit()
                editor?.clear()
                editor?.commit()

                Toast.makeText(this@WelcomeFragment.context, "¡Vuelve pronto!",
                    Toast.LENGTH_LONG).show()

                this@WelcomeFragment.activity?.finishAffinity()
            }
        }

        Picasso.get().load(CurrentUser.userImage).into(binding.imvTitle)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) ||
                super.onOptionsItemSelected(item)
    }
}
