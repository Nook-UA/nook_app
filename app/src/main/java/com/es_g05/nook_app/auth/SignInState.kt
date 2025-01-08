package com.es_g05.nook_app.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
