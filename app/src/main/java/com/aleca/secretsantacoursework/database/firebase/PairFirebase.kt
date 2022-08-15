package com.aleca.secretsantacoursework.database.firebase

import android.content.Context
import com.aleca.secretsantacoursework.database.storage.PairStorage
import com.aleca.secretsantacoursework.model.Pair
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val TABLE = "pair"

class PairFirebase {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference(TABLE)
    fun syncUsers(context: Context) {
        val userStorage = PairStorage(context)
        userStorage.open()
        val models: List<Pair> = userStorage.getFullList() as List<Pair>
        userStorage.close()
        database.removeValue()
        for (model in models) {
            database.push().setValue(model)
        }
    }
}