package com.wrmh.allmyfood.views.recipes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrmh.allmyfood.BuildConfig
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.adapters.CreateIngredientRecyclerAdapter
import com.wrmh.allmyfood.adapters.CreateStepRecyclerAdapter
import com.wrmh.allmyfood.databinding.FragmentCreateRecipeBinding
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.android.synthetic.main.fragment_create_recipe.*


/**
 * A simple [Fragment] subclass.
 */
private const val SHARED_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".myfileprovider"

class CreateRecipeFragment : Fragment() {
    private lateinit var ingredientAdapter: CreateIngredientRecyclerAdapter
    private lateinit var stepAdapter: CreateStepRecyclerAdapter
    private lateinit var binding: FragmentCreateRecipeBinding
    private var path: Uri = Uri.parse("")

    private val viewModel by lazy {
        ViewModelProvider(this).get(CreateRecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_create_recipe)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_recipe,
            container,
            false
        )

        binding.tvAddRecipeImage
            ?.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val permissions = arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )

                    ActivityCompat.requestPermissions(
                        this.requireActivity(),
                        permissions,
                        1
                    )

                    loadRecipeImage()
                } else {
                    loadRecipeImage()
                }
            }

        var stepList = ArrayList<String>()
        var ingredientList = ArrayList<String>()

        val deleteStep = ArrayList<Boolean>()
        val deleteIngredient = ArrayList<Boolean>()

        ingredientAdapter =
            CreateIngredientRecyclerAdapter(
                CreateIngredientRecyclerAdapter.OnChangeListener { element,
                                                                   pos,
                                                                   s ->
                    ingredientList[pos] = s.toString()
                },
                CreateIngredientRecyclerAdapter.OnClickListener { position, check ->
                    deleteIngredient[position] = check
                }
            )

        stepAdapter =
            CreateStepRecyclerAdapter(
                CreateStepRecyclerAdapter.OnChangeListener { element,
                                                             pos,
                                                             s ->
                    stepList[pos] = s.toString()
                },
                CreateStepRecyclerAdapter.OnClickListener { position, check ->
                    deleteStep[position] = check
                }
            )

        updateIngredientRecyclerView(ingredientList)
        updateStepRecyclerView(stepList)

        binding.btnAddIngredient.setOnClickListener {
            val auxList = ingredientList

            if (auxList.size > 0 && auxList.last().isEmpty()) {
                Toast.makeText(
                    this@CreateRecipeFragment.context, "Rellena primero todos los elementos",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                auxList.add(
                    ""
                )
                deleteIngredient.add(false)
            }

            ingredientList = auxList

            updateIngredientRecyclerView(ingredientList)
        }

        binding.btnAddStep.setOnClickListener {
            val auxList = stepList

            if (auxList.size > 0 && auxList.last().isEmpty()) {
                Toast.makeText(
                    this@CreateRecipeFragment.context, "Rellena primero todos los elementos",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                if (auxList.isEmpty())
                    auxList.add(
                        ""
                    )
                else
                    auxList.add(
                        ""
                    )

                deleteStep.add(false)
            }

            stepList = auxList

            updateStepRecyclerView(stepList)
        }

        binding.btnRmIngredient?.setOnClickListener {
            val auxList = ingredientList

            for (i in 0 until auxList.size - 1) {
                if (deleteIngredient[i]) {
                    deleteIngredient.removeAt(i)
                    auxList.removeAt(i)
                }
            }

            ingredientList = auxList

            updateStepRecyclerView(stepList)
            updateIngredientRecyclerView(ingredientList)
        }

        binding.btnRmStep?.setOnClickListener {
            val auxList = stepList

            for (i in 0 until auxList.size - 1) {
                if (deleteStep[i]) {
                    deleteStep.removeAt(i)
                    auxList.removeAt(i)
                }
            }

            stepList = auxList

            updateStepRecyclerView(stepList)
            updateIngredientRecyclerView(ingredientList)
        }

        binding.btnSaveRecipe.setOnClickListener {
            if (binding.createRecipeName?.text?.toString()?.isEmpty()!!) {
                Toast.makeText(
                    this@CreateRecipeFragment.context,
                    R.string.empty_field, Toast.LENGTH_LONG
                ).show()
                binding.createRecipeName!!.requestFocus()
            }

            if (binding.createRecipeDesc?.text?.toString()?.isEmpty()!!) {
                Toast.makeText(
                    this@CreateRecipeFragment.context,
                    R.string.empty_field, Toast.LENGTH_LONG
                ).show()
                binding.createRecipeDesc!!.requestFocus()
            } else {
                val recipeToSend = RecipeModel(
                    null,
                    CurrentUser.username!!,
                    binding.createRecipeName?.text.toString(),
                    binding.createRecipeDesc?.text.toString(),
                    null,
                    stepList,
                    ingredientList,
                    true,
                    ""
                )

                viewModel.createRecipe(
                    path, recipeToSend,
                    this@CreateRecipeFragment.context!!
                )
            }
        }

        binding.btnSaveRecipePublic?.setOnClickListener {
            if (binding.createRecipeName?.text?.toString()?.isEmpty()!!) {
                Toast.makeText(
                    this@CreateRecipeFragment.context,
                    R.string.empty_field, Toast.LENGTH_LONG
                ).show()
                binding.createRecipeName!!.requestFocus()
            }

            if (binding.createRecipeDesc?.text?.toString()?.isEmpty()!!) {
                Toast.makeText(
                    this@CreateRecipeFragment.context,
                    R.string.empty_field, Toast.LENGTH_LONG
                ).show()
                binding.createRecipeDesc!!.requestFocus()
            } else {
                val recipeToSend = RecipeModel(
                    null,
                    CurrentUser.username!!,
                    binding.createRecipeName?.text.toString(),
                    binding.createRecipeDesc?.text.toString(),
                    null,
                    stepList,
                    ingredientList,
                    false,
                    ""
                )

                viewModel.createRecipe(
                    path, recipeToSend,
                    this@CreateRecipeFragment.context!!
                )
            }
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                )
                    loadRecipeImage()
                else
                    Toast.makeText(
                        this.activity?.applicationContext, "Permission denied",
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
    }

    private fun updateStepRecyclerView(step: List<String>) {
        binding.recyclerViewCreateStep?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = stepAdapter

            stepAdapter.submitList(step)
        }
    }

    private fun updateIngredientRecyclerView(ingredient: List<String>) {
        binding.recyclerViewCreateIngredient?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ingredientAdapter

            ingredientAdapter.submitList(ingredient)
        }
    }

    private fun loadRecipeImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            path = data?.data!!
            imageViewRecipe?.setImageURI(path)
        }
    }
}
