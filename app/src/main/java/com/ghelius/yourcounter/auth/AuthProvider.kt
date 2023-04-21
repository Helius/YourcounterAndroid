package com.ghelius.yourcounter.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await


class AuthProvider() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    suspend fun authenticate(
        email: String,
        password: String
    ): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(
            email, password).await()
        return firebaseAuth.currentUser ?:
            throw FirebaseAuthException("", "")
    }

    companion object {
        private val TAG = "AuthProvider"
    }
}