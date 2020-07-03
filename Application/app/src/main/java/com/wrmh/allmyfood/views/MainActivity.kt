package com.wrmh.allmyfood.views

import android.content.Context
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.databinding.ActivityMainBinding
import com.wrmh.allmyfood.models.CurrentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

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

        CoroutineScope(Dispatchers.Main).launch {
            val apiService = API()

            val response =
                apiService.loginUserAsync(username.text.toString(), password.text.toString()).await()

            CurrentUser.onLoginSuccessful(response.username, response.fullname, response.userImage)

            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }
    }

    private fun writeSharedPreferences() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        with(sharedPref.edit()) {
            putString(getString(R.string.k_username), CurrentUser.username)
            putString(getString(R.string.k_fullname), CurrentUser.fullname)
            putString(getString(R.string.k_timestamp), CurrentUser.loginTimeStamp.toString())
            commit()
        }
    }

    private fun readSharedPreferences() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        val ts = Timestamp.valueOf(getString(R.string.k_timestamp))
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