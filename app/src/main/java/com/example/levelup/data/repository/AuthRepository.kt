package com.example.levelup.data.repository

import com.example.levelup.data.model.Credential

class AuthRepository (

    private val validCredential: Credential = Credential.Admin
){
    fun login(username:String, passoword: String): Boolean{
        return username ==validCredential.username && passoword == validCredential.password
    }
    fun register(email: String, pass: String): Boolean {
        return true
    }
}