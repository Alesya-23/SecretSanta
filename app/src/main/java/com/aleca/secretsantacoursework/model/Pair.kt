package com.aleca.secretsantacoursework.model

data class Pair(
    val id: Int,
    val userIdSanta: Int,
    val userIdRecipient: Int,
    val gameId: Int,
)