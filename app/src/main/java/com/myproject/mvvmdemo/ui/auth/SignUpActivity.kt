package com.myproject.mvvmdemo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.myproject.mvvmdemo.R
import com.myproject.mvvmdemo.data.entities.User
import com.myproject.mvvmdemo.databinding.SignUpActivityBinding
import com.myproject.mvvmdemo.ui.home.HomeActivity
import com.myproject.mvvmdemo.util.*
import kotlinx.android.synthetic.main.login_activity.progress_bar
import kotlinx.android.synthetic.main.quotes_item_view.*
import kotlinx.android.synthetic.main.sign_up_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance<AuthViewModelFactory>()

    private lateinit var binding: SignUpActivityBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                //signup_root_layout.snakebar("Register Success")
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignUp.setOnClickListener {
            userSignUp()
        }

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun userSignUp() {

        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextPasswordConfirm.text.toString().trim()

        if (name.isEmpty()) {
            toast("Enter Name")

            return
        }

        if (email.isEmpty()) {
            toast("Enter Name")

            return
        }

        if (password.isEmpty()) {
            toast("Enter Name")
            return
        }

        if (password != confirmPassword) {
            toast("Password mismatch")
            return
        }

        Coroutines.main {

            try {
                val signUpResponse = viewModel.userSignup(name, email, password)

                if (signUpResponse.user != null) {
                    viewModel.saveLoggedInUser(signUpResponse.user)

                }else{
                    binding.root.snakebar(signUpResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }

    }

}
