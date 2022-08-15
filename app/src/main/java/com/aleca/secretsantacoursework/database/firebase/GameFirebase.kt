package com.aleca.secretsantacoursework.database.firebase

import android.content.Context
import com.aleca.secretsantacoursework.database.storage.GameStorage
import com.aleca.secretsantacoursework.model.Game
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val TABLE = "game"

class GameFirebase {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference(TABLE)
    fun syncUsers(context: Context) {
        val userStorage = GameStorage(context)
        userStorage.open()
        val models: List<Game> = userStorage.getFullList() as List<Game>
        userStorage.close()
        database.removeValue()
        for (model in models) {
            database.push().setValue(model)
        }
    }
}