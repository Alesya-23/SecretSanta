package com.aleca.secretsantacoursework.view.ui.game

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.utils.ListGameAdapter
import com.aleca.secretsantacoursework.view.MainMenuActivity
import com.aleca.secretsantacoursework.view.USER_ID
import com.aleca.secretsantacoursework.viewmodel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val GAME_ID = "GAME_ID"
const val USER_ID_FOR_PAIR = "USER_ID_FOR_PAIR"

class GameFragment : Fragment() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var button: FloatingActionButton
    private lateinit var recyclerListGame: RecyclerView
    private var listGames = ArrayList<Game>()
    private var adapter = ListGameAdapter(listGames)
    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameViewModel =
            ViewModelProvider(this)[GameViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_list_games, container, false)
        gameViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        button = root.findViewById(R.id.button_add_game)
        recyclerListGame = root.findViewById(R.id.list_game)
        actionAddNewGameButton()
        downloadList()
        bindRecyclerList()
        return root
    }

    private fun actionAddNewGameButton() {
        button.setOnClickListener {
            val intent = Intent(activity?.baseContext, GameActivity::class.java)
            intent.putExtra(USER_ID, (activity as MainMenuActivity).homeViewModel.getIdUser())
            startActivity(intent)
        }
    }

    private fun downloadList() {
        val id = (activity as MainMenuActivity).getUser()
        userId = id
        listGames =
            activity?.applicationContext?.let { gameViewModel.getGames(id, it) } as ArrayList<Game>
    }

    private fun bindRecyclerList() {
        recyclerListGame.layoutManager = LinearLayoutManager(activity?.applicationContext)
        adapter = ListGameAdapter(listGames)
        recyclerListGame.adapter = adapter
        adapter.onItemClick = {
            val intent = Intent(activity?.baseContext, DetailsGameActivity::class.java)
            intent.putExtra(GAME_ID, it.id)
            intent.putExtra(USER_ID_FOR_PAIR, userId)
            startActivity(intent)
        }
    }
}