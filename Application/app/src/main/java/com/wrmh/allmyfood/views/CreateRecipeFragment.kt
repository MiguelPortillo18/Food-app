package com.wrmh.allmyfood.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.FragmentCreateRecipeBinding
import kotlinx.android.synthetic.main.fragment_create_recipe.*

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

        binding.tvAddRecipeImage
            ?.setOnClickListener{
                loadRecipeImage()
            }

       return binding.root
    }


    private fun loadRecipeImage(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            val path : Uri? = data?.getData()
            imageViewRecipe?.setImageURI(path)
        }
    }
}
