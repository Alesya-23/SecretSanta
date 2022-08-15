package com.aleca.secretsantacoursework.database.firebase

import android.content.Context
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val TABLE = "user"

class UserFirebase {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference(TABLE)
    fun syncUsers(context: Context) {
        val userStorage = UserStorage(context)
        userStorage.open()
        val models: List<User> = userStorage.getFullList() as List<User>
        userStorage.close()
        database.removeValue()
        for (model in models) {
            database.push().setValue(model)
        }
    }
}