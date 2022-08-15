package com.aleca.secretsantacoursework.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.User

class UserViewModel : ViewModel() {

    fun getListUser(context: Context): List<User?> {
        val userStorage = UserStorage(context)
        userStorage.open()
        val listUser = userStorage.getFullList()
        userStorage.close()
        return listUser
    }

    fun addUser(user: User, context: Context) {
        val userStorage = UserStorage(context)
        userStorage.insert(user)
        userStorage.close()
    }

    fun getUsersFromFirebase() {

    }

    fun getUsersFromDB() {

    }
}