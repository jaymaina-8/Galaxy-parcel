package com.jaytech.galaxytransporter.ui.theme.model


import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthResultState>(AuthResultState.Idle)
    val authState: StateFlow<AuthResultState> = _authState

    fun loginOrRegister(email: String, password: String) {
        _authState.value = AuthResultState.Loading

        // First, try sign in
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    _authState.value = AuthResultState.Success("Login successful")
                } else {
                    // Try registration if login fails
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { registerTask ->
                            if (registerTask.isSuccessful) {
                                _authState.value = AuthResultState.Success("Registered successfully")
                            } else {
                                _authState.value = AuthResultState.Failure(registerTask.exception?.message ?: "Auth failed")
                            }
                        }
                }
            }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthResultState.Idle
    }
}

sealed class AuthResultState {
    object Idle : AuthResultState()
    object Loading : AuthResultState()
    data class Success(val message: String) : AuthResultState()
    data class Failure(val error: String) : AuthResultState()
}
