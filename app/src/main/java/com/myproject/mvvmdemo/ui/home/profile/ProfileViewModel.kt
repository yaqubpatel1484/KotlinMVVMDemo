package com.myproject.mvvmdemo.ui.home.profile

import androidx.lifecycle.ViewModel
import com.myproject.mvvmdemo.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()
}
