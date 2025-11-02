package com.example.levelup.data.model

data class Credential (val username:String, val password:String) {

    companion object{
        val Admin =Credential(username="admin@duoc.cl", password="123456")
    }
}