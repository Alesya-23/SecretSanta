package com.aleca.secretsantacoursework.database.firebase

import android.content.Context
import com.aleca.secretsantacoursework.database.storage.UserGameStorage
import com.aleca.secretsantacoursework.model.UserGame
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val TABLE = "userGame"

class UserGameFirebase {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference(TABLE)
    fun syncUsers(context: Context) {
        val userStorage = UserGameStorage(context)
        userStorage.open()
        val models: List<UserGame> = userStorage.getFullList() as List<UserGame>
        userStorage.close()
        database.removeValue()
        for (model in models) {
            database.push().setValue(model)
        }
    }
}