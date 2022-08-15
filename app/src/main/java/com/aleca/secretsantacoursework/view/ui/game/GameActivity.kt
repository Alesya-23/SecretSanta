package com.aleca.secretsantacoursework.view.ui.game

import com.aleca.secretsantacoursework.database.firebase.PairFirebase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.view.USER_ID

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val id = intent.getIntExtra(USER_ID, -1)
        if (savedInstanceState == null) {
            val addNewGame = AddNewGameFragment.newInstance(id)
            supportFragmentManager.beginTransaction()
                .addToBackStack(addNewGame.tag)
                .replace(R.id.game_container, addNewGame)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val pairLogic = PairFirebase()
        pairLogic.syncUsers(this)
    }
}