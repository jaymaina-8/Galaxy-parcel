package com.jaytech.galaxytransporter.ui.theme.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun loginOrRegister(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Email and password must not be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("AuthViewModel", "Login successful")
                onSuccess()
            }
            .addOnFailureListener { loginError ->
                Log.d("AuthViewModel", "Login failed, trying to register: ${loginError.message}")
                // Try to register the user if login fails
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Log.d("AuthViewModel", "Registration successful")
                        onSuccess()
                    }
                    .addOnFailureListener { registerError ->
                        Log.e("AuthViewModel", "Registration failed: ${registerError.message}")
                        onError(registerError.message ?: "Authentication failed")
                    }
            }
    }
}
