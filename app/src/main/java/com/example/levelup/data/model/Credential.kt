package com.example.levelup.data.model

data class Credential (val username:String, val password:String) {

    companion object{
        val Admin =Credential(username="user@duoc.cl", password="hola123")
    }
}