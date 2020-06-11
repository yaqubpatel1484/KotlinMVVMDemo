package com.myproject.mvvmdemo.data.repositories

import com.myproject.mvvmdemo.data.db.AppDatabase
import com.myproject.mvvmdemo.data.entities.User
import com.myproject.mvvmdemo.data.network.responses.AuthResponse
import com.myproject.mvvmdemo.data.network.MyApi
import com.myproject.mvvmdemo.data.network.SafeApiRequest

class UserRepository(private val api: MyApi, private val db: AppDatabase) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {

        return apiRequest { api.userLogin(email, password) }
        /*
        here suspend function MyApi().userLogin should call only from suspend function or from Coroutines
        so we converted our this userLogin function suspended
         */
    }

    suspend fun signUp(name: String ,email: String, password: String ): AuthResponse {

        return apiRequest { api.userSignUp(name,email,password) }
    }


    // Inserting user details to local database
    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    // fetching stored record from local database
    fun getUser() = db.getUserDao().getUser()

}