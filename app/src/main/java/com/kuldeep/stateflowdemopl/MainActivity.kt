package com.kuldeep.stateflowdemopl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kuldeep.stateflowdemopl.databinding.ActivityMainBinding
import com.kuldeep.stateflowdemopl.viewmodel.LoginState
import com.kuldeep.stateflowdemopl.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.reflect.Array.get

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btn.setOnClickListener {
            viewModel.login(binding.edtUserName.text.toString(), binding.edtPass.text.toString())
        }

        //for access immmutable loginstate
        lifecycleScope.launch {
            viewModel.loginUiState.collect {
                when (it) {
                    is LoginState.Success -> {
                        Snackbar.make(
                            binding.root,
                            "SuccessFull",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        binding.pd.visibility = View.INVISIBLE

                    }

                    is LoginState.Error -> {
                        Snackbar.make(
                            binding.root,
                            it.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        binding.pd.visibility = View.INVISIBLE
                    }

                    is LoginState.Loading -> {
                        binding.pd.isVisible = true
                    }
                    else -> Unit
                }
            }

        }


    }
}