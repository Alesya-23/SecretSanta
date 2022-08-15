package com.aleca.secretsantacoursework.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.view.MainMenuActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user: User
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var editName: TextInputEditText
    private lateinit var editHobby: TextInputEditText
    private lateinit var buttonChange: MaterialButton
    private lateinit var buttonSave: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        editEmail = root.findViewById(R.id.email_input)!!
        editPassword = root.findViewById(R.id.password_editText)!!
        editName = root.findViewById(R.id.name_input)!!
        editHobby = root.findViewById(R.id.hobby_input)!!
        buttonChange = root.findViewById(R.id.button_change)!!
        buttonSave = root.findViewById(R.id.button_save)!!
        getUser()
        initFields()
        actionsButtons()
        return root
    }

    private fun getUser() {
        val id = (activity as MainMenuActivity).getUser()
        homeViewModel.setIdUser(id)
        user = activity?.applicationContext?.let { homeViewModel.getUser(it) }!!
    }

    private fun initFields() {
        editEmail.setText(user.email)
        editPassword.setText(user.password)
        editName.setText(user.name)
        editHobby.setText(user.hobbies)
        editEmail.isEnabled = false
        editPassword.isEnabled = false
        editName.isEnabled = false
        editHobby.isEnabled = false
    }

    private fun actionsButtons() {
        buttonChange.setOnClickListener {
            editEmail.isEnabled = true
            editPassword.isEnabled = true
            editName.isEnabled = true
            editHobby.isEnabled = true
        }
        buttonSave.setOnClickListener {
            val newUser = user
            newUser.email = editEmail.text.toString()
            newUser.name = editName.text.toString()
            newUser.password = editPassword.text.toString()
            newUser.hobbies = editHobby.text.toString()
            activity?.applicationContext?.let { it1 -> homeViewModel.changeUser(newUser, it1) }
            editEmail.isEnabled = false
            editPassword.isEnabled = false
            editName.isEnabled = false
            editHobby.isEnabled = false
        }
    }
}