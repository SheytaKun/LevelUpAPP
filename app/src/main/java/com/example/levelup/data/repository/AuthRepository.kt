package com.example.levelup.data.repository

import com.example.levelup.data.model.Credential

class AuthRepository (

    private val validCredential: Credential = Credential.Admin
){
    fun logi(username:String, passoword: String): Boolean{
        return username ==validCredential.username && passoword == validCredential.password
    }
}