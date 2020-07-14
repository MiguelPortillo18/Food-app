package com.wrmh.allmyfood.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.ActivityMainBinding
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.views.HomeActivity
import com.wrmh.allmyfood.views.RegisterActivity
import timber.log.Timber
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var spinner: ProgressBar

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
            spinner = progressBar1!!
            spinner.bringToFront()
        }

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.callback = {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

            with(sharedPref.edit()) {
                putString(getString(R.string.k_username), CurrentUser.username)
                putString(getString(R.string.k_fullname), CurrentUser.fullname)
                putString(getString(R.string.k_userImage), CurrentUser.userImage)
                apply()
            }

            startActivity(Intent(this, HomeActivity::class.java))
        }

        val sharedPref = this.getSharedPreferences("USER_LOG", Context.MODE_PRIVATE) ?: return

        val lastLoggedUsername = sharedPref.getString(getString(R.string.k_username), "")
        val lastLoggedUserFullname = sharedPref.getString(getString(R.string.k_fullname), "")
        val lastLoggedUserImage = sharedPref.getString(getString(R.string.k_userImage), "")

        if (lastLoggedUsername!!.isEmpty())
            return

        CurrentUser.onLoginSuccessful(
            lastLoggedUsername,
            lastLoggedUserFullname!!,
            lastLoggedUserImage!!
        )

        supportActionBar?.hide()

        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        GoogleSignIn.getLastSignedInAccount(this)

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
                R.string.empty_field,
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

        this.getPreferences(Context.MODE_PRIVATE) ?: return

        viewModel.loginUser(
            username.text.toString(),
            password.text.toString(),
            this,
            spinner
        )
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        val account = task.getResult(ApiException::class.java)

        viewModel.checkForGoogleSignIn(this, account!!, spinner)
    }
}