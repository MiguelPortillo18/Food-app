package com.example.allmyfood.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.allmyfood.R
import com.example.allmyfood.api.RetrofitClient
import com.example.allmyfood.databinding.ActivityRegisterBinding
import com.example.allmyfood.models.CurrentUser
import com.example.allmyfood.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_register.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        RetrofitClient.instance.createUser(
            username.text.toString(),
            fullname.text.toString(),
            password.text.toString(),
            email.text.toString()
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Algo salió mal . . .", Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                CurrentUser.onLoginSuccessful(username.text.toString(), fullname.text.toString())

                startActivity(
                    Intent(
                        this@RegisterActivity,
                        HomeActivity::class.java
                    )
                )
            }
        })
    }
}