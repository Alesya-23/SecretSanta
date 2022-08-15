package com.aleca.secretsantacoursework.view.ui.game

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.viewmodel.DetailsGameViewModel

class PairDetailActivity : AppCompatActivity() {

    private val viewModel: DetailsGameViewModel by viewModels()
    private var pairIdArgument: Int = 0
    private lateinit var nameUser: TextView
    private lateinit var descriptionHobbyUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pair_user)
        pairIdArgument = intent.getIntExtra(USER_ID_FOR_PAIR, -1)
        nameUser = findViewById(R.id.name_user)
        descriptionHobbyUser = findViewById(R.id.description_hobby_user)
        getUser()
        this.title = getString(R.string.title_pair)
    }

    private fun getUser() {
        val user = this.let { viewModel.getUser(pairIdArgument, it) }
        if (user != null) {
            nameUser.text = user.name
            descriptionHobbyUser.text = user.hobbies
        }
    }
}