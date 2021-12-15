package com.kuldeep.stateflowdemopl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    //mutable
    var _loginUiState = MutableStateFlow<LoginState>(LoginState.Empty)

    //immutable
    val loginUiState = _loginUiState

    fun login(userName: String, password: String) = viewModelScope.launch {
        _loginUiState.value = LoginState.Loading

        delay(1000)

        if (userName == "hey" && password == "pass"){
            _loginUiState.value = LoginState.Success
        } else{
            _loginUiState.value = LoginState.Error("Invalid Auth!")
        }

    }


}

sealed class LoginState{

    object Success: LoginState()
    data class Error(val message: String) : LoginState()
    object Loading: LoginState()
    object Empty: LoginState()

}