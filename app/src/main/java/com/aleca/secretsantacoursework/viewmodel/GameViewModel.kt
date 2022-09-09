package com.aleca.secretsantacoursework.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aleca.secretsantacoursework.database.storage.GameStorage
import com.aleca.secretsantacoursework.database.storage.UserGameStorage
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.UserGame

class GameViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getGames(idUs: Int, context: Context): List<Game?> {
        val gameUser = UserGameStorage(context)
        gameUser.open()
        val listGamesNowUser: ArrayList<UserGame> =
            gameUser.getFilterListGames(idUs) as ArrayList<UserGame>
        gameUser.close()
        val gameStorage = GameStorage(context)
        gameStorage.open()
        val list = ArrayList<Game>()
        for (games in listGamesNowUser) {
            val game = gameStorage.getElement(games.gameId)
            if (game != null) {
                list.add(game)
            }
        }
        gameStorage.close()
        return list
    }

    fun getGame(idGame: Int, context: Context): Game? {
        val gameStorage = GameStorage(context)
        gameStorage.open()
        val game = gameStorage.getElement(idGame)
        gameStorage.close()
        return game
    }

    fun deleteGame(idGame: Int,context: Context){
        val gameStorage = GameStorage(context)
        gameStorage.open()
        gameStorage.delete(idGame)
        gameStorage.close()
    }
}