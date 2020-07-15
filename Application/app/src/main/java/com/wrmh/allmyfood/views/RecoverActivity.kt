package com.wrmh.allmyfood.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.databinding.ActivityRecoverBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecoverActivity : AppCompatActivity() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityRecoverBinding>(
                this,
                R.layout.activity_recover
            )

        binding.btnRecPass.setOnClickListener {
            if (binding.etRecPass.text.isNotEmpty()) {
                recoverPassword(binding.etRecPass.text.toString())
            }
        }
    }

    private fun recoverPassword(email: String) {
        coroutineScope.launch {
            val recoverPasswordDeferred = API().recoverPasswordAsync(email)

            try {
                val apiResponse = recoverPasswordDeferred.await()

                if (apiResponse.error)
                    throw java.lang.Exception()

                Toast.makeText(
                    applicationContext, "Se ha enviado tu nueva contraseña",
                    Toast.LENGTH_LONG
                ).show()

                finishActivity(0)
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext, R.string.error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}