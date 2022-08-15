package com.aleca.secretsantacoursework.model

data class User(
    val id: Int,
    var email: String,
    var password: String,
    var name: String,
    var hobbies: String
)