package com.wrmh.allmyfood.views

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.databinding.ActivityRegisterBinding
import com.wrmh.allmyfood.models.CurrentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

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
    }

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