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
            onRegisterClick(binding)
        }
    }

    private fun onRegisterClick(binding: ActivityRegisterBinding) {

        if (binding.etRegUsr.text.isEmpty()) {
            binding.etRegUsr.requestFocus()
            Toast.makeText(applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (binding.etRegFullname.text.isEmpty()) {
            binding.etRegFullname.requestFocus()
            Toast.makeText(applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (binding.etRegPass.text.isEmpty()) {
            binding.etRegPass.requestFocus()
            Toast.makeText(applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (binding.etRegConfirm.text.isEmpty()) {
            binding.etRegConfirm.requestFocus()
            Toast.makeText(applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (binding.etRegEmail.text.isEmpty()) {
            binding.etRegEmail.requestFocus()
            Toast.makeText(applicationContext,
                "Te faltan campos por completar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(binding.etRegConfirm.text.toString() != binding.etRegPass.text.toString()) {
            Toast.makeText(
                applicationContext,
                "Las contraseñas no coinciden :(",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        RetrofitClient.instance.createUser(
            binding.etRegUsr.text.toString(),
            binding.etRegFullname.text.toString(),
            binding.etRegPass.text.toString(),
            binding.etRegEmail.text.toString()
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(applicationContext,
                    "Algo salió mal . . .", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(
                    applicationContext, "Bienvenido ${binding.etRegUsr.text.toString()}",
                    Toast.LENGTH_LONG
                ).show()

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