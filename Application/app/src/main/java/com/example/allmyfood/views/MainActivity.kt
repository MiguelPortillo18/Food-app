package com.example.allmyfood.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.allmyfood.R
import com.example.allmyfood.api.RetrofitClient
import com.example.allmyfood.databinding.ActivityMainBinding
import com.example.allmyfood.models.CurrentUser
import com.example.allmyfood.models.LoginResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        binding.apply {
            signInButton.setOnClickListener(onClick)
            tvRegister.setOnClickListener(onClickRegisterTv)
            btnLogin.setOnClickListener {
                onClickBtnLogin(binding.etUsername, binding.etPassword)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        var account = GoogleSignIn.getLastSignedInAccount(this)

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private var onClick = View.OnClickListener {
        when (it.id) {
            R.id.sign_in_button -> signIn()
        }
    }

    private var onClickRegisterTv = View.OnClickListener {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun onClickBtnLogin(username: EditText, password: EditText) {
        if (username.text.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "No puedes dejar campos vacios . . .",
                Toast.LENGTH_LONG
            ).show()

            username.requestFocus()
            return
        }

        if (password.text.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "No puedes dejar campos vacios . . .",
                Toast.LENGTH_LONG
            ).show()

            password.requestFocus()
            return
        }

        RetrofitClient.instance.loginUser(
            username.text.toString(),
            password.text.toString()
        ).enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext,
                    "Algo salió mal . . .", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.body()?.error!!){
                    Toast.makeText(applicationContext,
                        "Algo salió mal . . .", Toast.LENGTH_LONG).show()
                    return
                }
                CurrentUser.onLoginSuccessful(response.body()!!.username,
                    response.body()!!.fullname)

                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            }
        })
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            var account = task.getResult(ApiException::class.java)
        } catch (e: ApiException) {

        }
    }
}
