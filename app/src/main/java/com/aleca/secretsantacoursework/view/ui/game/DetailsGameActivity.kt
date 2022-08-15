package com.aleca.secretsantacoursework.view.ui.game

import android.content.Intent
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.viewmodel.AddNewGameViewModel
import com.aleca.secretsantacoursework.viewmodel.DetailsGameViewModel

class DetailsGameActivity : AppCompatActivity() {
    private lateinit var nameGame: TextView
    private lateinit var dateStart: TextView
    private lateinit var dateEnd: TextView
    private lateinit var countGamers: TextView
    private lateinit var listViewGamers: ListView
    private lateinit var buttonSave: Button
    private lateinit var buttonSeePair: Button
    private lateinit var listPeopleGame: ArrayList<User>
    private lateinit var listGamers: ArrayList<String>
    private lateinit var listPeoplesAll: ArrayList<User>
    private lateinit var listPeoplesAllInGame: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_game_activity)
        gameId = intent.getIntExtra(GAME_ID, -1)
        userId = intent.getIntExtra(USER_ID_FOR_PAIR, -1)
        nameGame = findViewById(R.id.name_game_edit)
        dateStart = findViewById(R.id.datePickerStartTextView)
        dateEnd = findViewById(R.id.datePickerEndTextView)
        countGamers = findViewById(R.id.count_people_edit)
        listViewGamers = findViewById(R.id.game_list_detail)
        buttonSave = findViewById(R.id.add_game_button)
        buttonSeePair = findViewById(R.id.saw_pair)
        listGamers = ArrayList()
        listPeopleGame = ArrayList()
        listPeoplesAll = ArrayList()
        getGame()
        game?.let { fillGame(it) }
        initList()
        buttonSave()
        buttonSeePairMy()
    }

    private val viewModel: DetailsGameViewModel by viewModels()
    private val addGameViewModel: AddNewGameViewModel by viewModels()
    private var gameId = 0
    private var userId = 0
    var game: Game? = null

    private fun getGame() {
        game = viewModel.getGame(gameId, this)
        if (game == null) {
            Toast.makeText(this, getString(R.string.sign_in_error_toast), Toast.LENGTH_SHORT)
                .show()
        }
        game?.let { fillGame(it) }
    }

    private fun getGamers(): ArrayList<String> {
        listPeoplesAllInGame = this.let { viewModel.getGamers(gameId, it) }
        val list = ArrayList<String>()
        for (i in 0 until listPeoplesAllInGame.size) {
            list.add(listPeoplesAllInGame[i].name + " " + listPeoplesAllInGame[i].email)
        }
        return list
    }

    private fun getPeoples(): ArrayList<String> {
        listPeoplesAll = this.let { viewModel.getPeoples(it) } as ArrayList<User>
        for (i in 0 until listPeoplesAll.size) {
            listGamers.add(listPeoplesAll[i].name + " " + listPeoplesAll[i].email)
        }
        return listGamers
    }

    private fun initList() {
        listViewGamers.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listGamers = getPeoples()
        val arrayAdapter = ArrayAdapter(
            this.applicationContext,
            R.layout.item_people_in_game_small, listGamers
        )
        val gamers = getGamers()
        listViewGamers.adapter = arrayAdapter
        for (i in 0 until gamers.size) {
            for (j in 0 until listGamers.size) {
                if (gamers[i] == listGamers[j]) {
                    listViewGamers.setItemChecked(j, true)
                }
            }
        }
    }

    private fun getListGamers() {
        listPeopleGame.clear()
        val sbArray: SparseBooleanArray = listViewGamers.checkedItemPositions
        for (i in 0 until sbArray.size()) {
            val key = sbArray.keyAt(i)
            if (sbArray[key]) {
                listPeopleGame.add(listPeoplesAll[key])
            }
        }
        if (listPeopleGame.size <= 1) {
            listPeopleGame.clear()
            Toast.makeText(this, R.string.attantion_toast_count_gamers, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillGame(game: Game) {
        nameGame.text = game.name
        dateStart.text = game.dateStart
        dateEnd.text = game.dateEnd
        countGamers.text = game.countGamers.toString()
    }

    private fun buttonSave() {
        buttonSave.setOnClickListener {
            updateUserData()
        }
    }

    private fun buttonSeePairMy() {
        buttonSeePair.setOnClickListener {
            var userNowRecipientId = 0
            userNowRecipientId =
                game?.let { it1 -> viewModel.getPecipientId(userId, it1.id, this) }!!
//            val fragment = userNowRecipientId.let { it1 -> PairUserFragment.newInstance(it1) }
//            supportFragmentManager.beginTransaction()
//                    .addToBackStack(fragment.tag)
//                    .replace(R.id.detail_activity_container, fragment)
//                    .commit()
              val intent = Intent(this, PairDetailActivity::class.java)
            intent.putExtra(USER_ID_FOR_PAIR, userNowRecipientId)
            startActivity(intent)
        }
    }

    private fun updateUserData() {
        getListGamers()
        val nameGame = nameGame.text.toString()
        val dateStart = dateStart.text.toString()
        val dateEnd = dateEnd.text.toString()
        if (checkFieldsNewGame()) {
            addGameViewModel.setIdGame(gameId)
            viewModel.changeGame(
                Game(gameId, nameGame, dateStart, dateEnd, listPeopleGame.size),
                this
            )
            viewModel.clearPairs(gameId, this)
            addGameViewModel.generatePairs(listPeopleGame, this)
            addGameViewModel.bindGameToUsers(listPeopleGame, this)
            getGame()
        } else {
            Toast.makeText(this, R.string.toast_dont_fill_field, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFieldsNewGame(): Boolean {
        if (nameGame.text?.isNotEmpty() == true &&
            dateStart.text.isNotEmpty() &&
            dateEnd.text.isNotEmpty() &&
            dateEnd.text != dateStart.text
            && listPeopleGame.isNotEmpty()
        )
            return true
        return false
    }
}