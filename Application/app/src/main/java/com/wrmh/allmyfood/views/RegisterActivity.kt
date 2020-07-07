package com.wrmh.allmyfood.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.databinding.ActivityRegisterBinding
import com.wrmh.allmyfood.models.CurrentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var uriImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.title_register)

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRegisterBinding>(
            this,
            R.layout.activity_register
        )

        binding.btnRegister.setOnClickListener {
            onRegisterClick(
                binding.etRegUsr,
                binding.etRegFullname, binding.etRegPass, binding.etRegConfirm, binding.etRegEmail
            )
        }

        //Agregar foto
        imageView = findViewById<View>(R.id.imv_id) as ImageView

        imageView.setOnClickListener{
            fun onClick(v: View?) {
                openGallery()
            }
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }

    fun onActivityResult(requestCode : Int, resultCode : Int, intent){
        if(resultCode == RESULT_OK && requestCode == 100){
            uriImage
            imageView.setImageURI(uriImage)
        }
    }
    //Hasta aca

    private fun onRegisterClick(
        username: EditText, fullname: EditText,
        password: EditText, confirmPass: EditText, email: EditText
    ) {
        if (username.text.isEmpty()) {
            username.requestFocus()
            Toast.makeText(
                applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (fullname.text.isEmpty()) {
            fullname.requestFocus()
            Toast.makeText(
                applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (password.text.isEmpty()) {
            password.requestFocus()
            Toast.makeText(
                applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (confirmPass.text.isEmpty()) {
            confirmPass.requestFocus()
            Toast.makeText(
                applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (email.text.isEmpty()) {
            email.requestFocus()
            Toast.makeText(
                applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (password.text.toString() != confirmPass.text.toString()) {
            Toast.makeText(
                applicationContext,
                "Las contraseñas no coinciden :(",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val apiService = API()

            val response = apiService.createUserAsync(
                username.text.toString(),
                fullname.text.toString(),
                password.text.toString(),
                email.text.toString()
            )

            CurrentUser.onLoginSuccessful(
                username.text.toString(), fullname.text.toString(),
                ""
            )
        }
    }
}