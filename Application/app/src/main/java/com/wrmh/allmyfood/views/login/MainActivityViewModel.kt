package com.wrmh.allmyfood.views.login

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    lateinit var callback: () -> Unit

    fun loginUser(
        username: String, password: String, context: Context, pb: ProgressBar
    ) {
        coroutineScope.launch {
            pb.bringToFront()
            pb.visibility = View.VISIBLE
            val getLoginDeferred = API().loginUserWithNameAsync(username, password)

            try {
                val apiResponse = getLoginDeferred.await()
                CurrentUser.onLoginSuccessful(username, apiResponse.fullname, apiResponse.userImage)
                pb.visibility = View.GONE

                callback()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.error,
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun checkForGoogleSignIn(context: Context, account: GoogleSignInAccount) {
        coroutineScope.launch {
            val getRegisterDeferred = API().createUserAsync(
                account.email!!,
                account.displayName!!,
                account.id!!,
                account.photoUrl.toString()
            )

            try {
                val registerResponse = getRegisterDeferred.await()

                if (!registerResponse.error) {
                    val getLoginDeferred = API().loginUserWithEmailAsync(
                        account.email!!,
                        account.id!!
                    )
                    val loginResponse = getLoginDeferred.await()
                    CurrentUser.onLoginSuccessful(
                        loginResponse.username, loginResponse.fullname,
                        loginResponse.userImage
                    )
                } else {
                    CurrentUser.onLoginSuccessful(
                        account.email!!,
                        account.displayName!!,
                        account.photoUrl.toString()
                    )
                }

                callback()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}