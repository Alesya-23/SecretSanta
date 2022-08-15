package com.aleca.secretsantacoursework.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aleca.secretsantacoursework.database.storage.GameStorage
import com.aleca.secretsantacoursework.database.storage.PairStorage
import com.aleca.secretsantacoursework.database.storage.UserGameStorage
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.Pair
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.model.UserGame

class AddNewGameViewModel : ViewModel() {
    private var idGame: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setIdGame(key: Int) {
        idGame.value = key
    }

    private fun getIdGame() = idGame.value

    @SuppressLint("StaticFieldLeak")
    private lateinit var contextViewModel: Context
    private lateinit var gameNew: Game


    fun addNewGame(game: Game, context: Context) {
        gameNew = game
        val gameStorage = GameStorage(context)
        gameStorage.open()
        gameStorage.insert(game)
        val gameNow = gameStorage.getFullList().find { it?.name == gameNew.name }
        if (gameNow != null) {
            setIdGame(gameNow.id)
        }
        gameStorage.close()
    }

    fun generatePairs(listPeople: ArrayList<User>, context: Context) {
        val pairStorage = PairStorage(context)
        val listPair: ArrayList<Pair> = ArrayList()
        pairStorage.open()
        for (i in 0 until listPeople.size) {
            if (i == listPeople.size - 1) {
                getIdGame()?.let { Pair(0, listPeople[i].id, listPeople[0].id, it) }?.let {
                    listPair.add(
                        it
                    )
                }
                getIdGame()?.let {
                    Pair(
                        0, listPeople[i].id, listPeople[0].id,
                        it
                    )
                }?.let { pairStorage.insert(it) }
            } else {
                getIdGame()?.let {
                    Pair(
                        0, listPeople[i].id, listPeople[i + 1].id,
                        it
                    )
                }?.let { listPair.add(it) }
                getIdGame()?.let { it ->
                    Pair(
                        0, listPeople[i].id, listPeople[i + 1].id,
                        it
                    ).let { pairStorage.insert(it) }
                }
            }
        }
        pairStorage.close()
    }

    fun bindGameToUsers(listPeople: ArrayList<User>, context: Context) {
        val userGameStorage = UserGameStorage(context)
        userGameStorage.open()
        for (i in 0 until listPeople.size) {
            getIdGame()?.let { UserGame(listPeople[i].id, it) }?.let { userGameStorage.insert(it) }
        }
        userGameStorage.close()
    }

    fun getPeoples(context: Context): List<User?> {
        val storageUser = UserStorage(context)
        return storageUser.getFullList()
    }
}