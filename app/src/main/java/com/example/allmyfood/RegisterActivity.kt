package com.example.allmyfood

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById<Button>(R.id.btn_register).setOnClickListener(onClickRegister)
    }

    private var onClickRegister = View.OnClickListener {
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}