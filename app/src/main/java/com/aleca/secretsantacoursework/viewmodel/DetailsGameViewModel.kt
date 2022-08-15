package com.aleca.secretsantacoursework.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aleca.secretsantacoursework.database.storage.GameStorage
import com.aleca.secretsantacoursework.database.storage.PairStorage
import com.aleca.secretsantacoursework.database.storage.UserGameStorage
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.Pair
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.model.UserGame

class DetailsGameViewModel : ViewModel() {

    fun getGame(id: Int, context: Context): Game? {
        val gameStorage = GameStorage(context)
        gameStorage.open()
        val game = gameStorage.getElement(id)
        gameStorage.close()
        return game
    }

    fun getPairs(id: Int, context: Context): List<Pair?> {
        val pair = PairStorage(context)
        pair.open()
        val pairs = pair.getFilterList(id)
        pair.close()
        return pairs
    }

    fun getPecipientId(idUser: Int, idGame: Int, context: Context): Int {
        val gameUser = PairStorage(context)
        gameUser.open()
        val listPeopleNowGame: ArrayList<Pair> =
            gameUser.getFilterList(idGame) as ArrayList<Pair>
        val pair = listPeopleNowGame.find { it.userIdSanta == idUser }
        gameUser.close()
        if (pair != null) {
            return pair.userIdRecipient
        }
        return -1
    }

    fun getUser(id: Int, context: Context): User? {
        val userStorage = UserStorage(context)
        userStorage.open()
        val user = userStorage.getElement(id)
        userStorage.close()
        return user
    }

    fun getGamers(id: Int, context: Context): ArrayList<User> {
        val gameUser = UserGameStorage(context)
        gameUser.open()
        val listPeopleNowGame: ArrayList<UserGame> =
            gameUser.getFilterList(id) as ArrayList<UserGame>
        gameUser.close()
        val userStorage = UserStorage(context)
        userStorage.open()
        val list = ArrayList<User>()
        for (peoples in listPeopleNowGame) {
            val user = userStorage.getElement(peoples.userId)
            if (user != null) {
                list.add(user)
            }
        }
        userStorage.close()
        return list
    }

    fun getPeoples(context: Context): List<User?> {
        val storageUser = UserStorage(context)
        return storageUser.getFullList()
    }

    fun changeGame(game: Game, context: Context) {
        val gameStorage = GameStorage(context)
        gameStorage.open()
        gameStorage.update(game)
        gameStorage.close()
    }

    fun clearPairs(id: Int, context: Context) {
        val gameUser = UserGameStorage(context)
        val pairStorage = PairStorage(context)
        pairStorage.open()
        gameUser.open()
        val listUsers = gameUser.getFilterList(id)
        for (i in listUsers.indices) {
            listUsers[i]?.let {
                listUsers[i]?.let { it1 ->
                    gameUser.delete(
                        it.userId,
                        it1.gameId
                    )
                }
            }
            pairStorage.delete(id)
        }
        gameUser.close()
        pairStorage.close()
    }
}