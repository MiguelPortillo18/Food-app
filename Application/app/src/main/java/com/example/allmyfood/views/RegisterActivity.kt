package com.example.allmyfood

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.title_register)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById<Button>(R.id.btn_register).setOnClickListener(onClickRegister)
    }

    private var onClickRegister = View.OnClickListener {
        var username = findViewById<EditText>(R.id.et_regUsr)
        var fullname = findViewById<EditText>(R.id.et_regFullname)
        var password = findViewById<EditText>(R.id.et_regPass)
        var email = findViewById<EditText>(R.id.et_regEmail)


    }
}