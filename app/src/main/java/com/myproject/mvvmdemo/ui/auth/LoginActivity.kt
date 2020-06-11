package com.myproject.mvvmdemo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.myproject.mvvmdemo.R
import com.myproject.mvvmdemo.databinding.LoginActivityBinding
import com.myproject.mvvmdemo.ui.home.HomeActivity
import com.myproject.mvvmdemo.util.ApiException
import com.myproject.mvvmdemo.util.NoInternetException
import com.myproject.mvvmdemo.util.snakebar
import com.myproject.mvvmdemo.util.toast
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance<AuthViewModelFactory>()

    private lateinit var binding: LoginActivityBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.login_activity)

        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                root_layout.snakebar("Welcome")
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }

        })

        binding.buttonSignIn.setOnClickListener {
            userLogin()
        }

        binding.textViewSignUp.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }

    private fun userLogin() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            toast("Invalid Credentials")
            return
        }


        // 'lifecycleScope.launch{}' is another way to write suspend function or we can say 'lifecycleScope.launch{}' is also a Coroutine
        lifecycleScope.launch {
            try {
                val loginResponse = viewModel.userLogin(email, password)
                if (loginResponse.user != null) {
                    viewModel.saveLoggedInUser(loginResponse.user)
                } else {
                    binding.rootLayout.snakebar(loginResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }

    }

}
