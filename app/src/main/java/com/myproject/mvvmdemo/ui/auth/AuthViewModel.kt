package com.myproject.mvvmdemo.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.myproject.mvvmdemo.data.entities.User
import com.myproject.mvvmdemo.data.repositories.UserRepository
import com.myproject.mvvmdemo.util.ApiException
import com.myproject.mvvmdemo.util.Coroutines
import com.myproject.mvvmdemo.util.NoInternetException
import java.io.IOException

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    fun getLoggedInUser()  = repository.getUser()

    suspend fun userLogin(email: String ,password: String ) =
        repository.userLogin(email,password)

    suspend fun saveLoggedInUser(user: User) =
        repository.saveUser(user)

    suspend fun userSignup(name: String ,email: String ,password: String ) =
        repository.signUp(name,email,password)


}

