package com.example.allmyfood

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        setContentView(R.layout.activity_main)
        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener(onClick)
        findViewById<TextView>(R.id.tv_register).setOnClickListener(onClickRegisterTv)
        findViewById<Button>(R.id.btn_login).setOnClickListener(onClickBtnLogin)
    }

    override fun onStart() {
        super.onStart()
        var account = GoogleSignIn.getLastSignedInAccount(this)

        var signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private var onClick = View.OnClickListener{
        when(it.id) {
            R.id.sign_in_button -> signIn()
        }
    }

    private var onClickRegisterTv = View.OnClickListener {
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private var onClickBtnLogin = View.OnClickListener {
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun signIn(){
        var signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>){
        try{
            var account = task.getResult(ApiException::class.java)
        }
        catch(e: ApiException){

        }
    }
}
