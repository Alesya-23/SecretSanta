package com.aleca.secretsantacoursework.view.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.User

class HomeViewModel : ViewModel() {

    private var idUser: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setIdUser(key: Int) {
        idUser.value = key
    }

    fun getIdUser() = idUser.value

    fun getUser(context: Context): User? {
        return if (getIdUser() != null) {
            val userStorage = UserStorage(context)
            userStorage.open()
            val listUser = userStorage.getFullList()
            val userOur = listUser.find { it?.id == idUser.value }
            userStorage.close()
            userOur
        } else
            User(
                id = -1,
                email = "Not found",
                password = "Not found",
                name = "Not found",
                hobbies = "none"
            )
    }

    fun changeUser(user: User, context: Context) {
        val userStorage = UserStorage(context)
        userStorage.open()
        userStorage.update(user)
        userStorage.close()
    }

}