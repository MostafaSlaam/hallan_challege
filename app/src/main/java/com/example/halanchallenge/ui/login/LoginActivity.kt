package com.example.halanchallenge.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.halanchallenge.R
import com.example.halanchallenge.databinding.ActivityLoginBinding
import com.example.halanchallenge.model.LoginResponse
import com.example.halanchallenge.ui.productList.ProductsListActivity
import com.example.halanchallenge.util.Validator.validatePassword
import com.example.halanchallenge.util.Validator.validateUsername
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    //    Login login;
    lateinit var viewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeViewModel()
        binding.loginButton.setOnClickListener {
            if (checkData())
                lifecycleScope.launch {
                    viewModel.userIntent.send(
                        LoginIntent.Login(
                            binding.usernameEt.text.toString(), binding.passwordEt.text.toString()
                        )
                    )
                }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is LoginState.Idle -> {

                    }
                    is LoginState.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }

                    is LoginState.LoginDone -> {
                        binding.progressbar.visibility = View.GONE
                        openProductsActivity(it.response)

                    }
                    is LoginState.Error -> {
                        Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_LONG).show()
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }

        }
    }

    fun openProductsActivity(loginResponse: LoginResponse) {
        val myIntent = Intent(this@LoginActivity, ProductsListActivity::class.java)
        myIntent.putExtra("RESPONSE", loginResponse)
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(myIntent)
    }

    fun checkData(): Boolean {
        if (binding.usernameEt.text.toString().isEmpty()) {
            binding.usernameEt.error = "الاسم مطلوب"
            return false
        } else if (!validateUsername(binding.usernameEt.text.toString())) {
            binding.usernameEt.error = "الاسم غير صحيح"
            return false
        } else
            binding.usernameEt.error = null

        if (binding.passwordEt.text.toString().isEmpty()) {
            binding.passwordEt.error = "كلمة السر مطلوبة"
            return false
        } else if (!validatePassword(binding.passwordEt.text.toString())) {
            binding.passwordEt.error = "كلمة السر غير صحيحه"
            return false
        } else
            binding.passwordEt.error = null

        return true


    }
}