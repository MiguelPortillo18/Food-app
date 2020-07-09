package com.wrmh.allmyfood.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.ActivityRegisterBinding
import com.wrmh.allmyfood.models.CurrentUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var circleImageView : CircleImageView
    private lateinit var spinner: ProgressBar

    private val viewModel by lazy {
        ViewModelProvider(this).get(RegisterActivityViewModel::class.java)
    }

    private var path: Uri = Uri.parse("")

    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.title_register)

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRegisterBinding>(
            this,
            R.layout.activity_register
        )

        spinner = binding.progressBarReg!!
        spinner.bringToFront()

        binding.btnRegister.setOnClickListener {
            onRegisterClick(
                binding.etRegUsr,
                binding.etRegFullname, binding.etRegPass, binding.etRegConfirm, binding.etRegEmail
            )
        }

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

        circleImageView = binding.imageViewProfile as CircleImageView
    }

    fun onClick(view: View) {
        loadImage()
    }

    private fun loadImage(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            path = data?.data!!
            circleImageView.setImageURI(path)
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

        viewModel.createUser(
            path,
            username.text.toString(),
            password.text.toString(),
            fullname.text.toString(),
            email.text.toString(),
            this,
            spinner
        )
    }

}