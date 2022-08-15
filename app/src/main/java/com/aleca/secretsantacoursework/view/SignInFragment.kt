package com.aleca.secretsantacoursework.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.databinding.SignInFragmentBinding
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

const val USER_ID = "USER_ID"

class SignInFragment : Fragment(R.layout.sign_in_fragment) {
    private lateinit var signInFragmentBinding: SignInFragmentBinding
    private var login: String = ""
    private var password: String = ""
    private var isUserExist: Boolean = false
    private var isDataInCorrect: Boolean = false
    private lateinit var user: User
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInFragmentBinding = SignInFragmentBinding.bind(view)
        actionButtons()
        FirebaseAuth.getInstance().signOut()
    }

    private fun signIn() {
        val check = checkUser()
        if (check) {
            Toast.makeText(
                activity?.applicationContext,
                getString(R.string.sign_in_toast),
                Toast.LENGTH_SHORT
            ).show()
            //userViewModel.setIdUser(user.id)
            val intent = Intent(activity?.applicationContext, MainMenuActivity::class.java)
            intent.putExtra(USER_ID, user.id)
            startActivity(intent)
        } else {
            Toast.makeText(
                activity?.applicationContext,
                getString(R.string.sign_in_error_toast),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkFillFields(): Boolean {
        if (password.isEmpty() && login.isEmpty()) {
            signInFragmentBinding.incorrectData.text =
                getString(R.string.sign_in_error_fields_empty_toast)
            return false
        }
        if (login.isEmpty()) {
            signInFragmentBinding.incorrectData.text =
                getString(R.string.sign_in_error_field_login_empty_toast)
            return false
        }
        if (password.isEmpty()) {
            signInFragmentBinding.incorrectData.text =
                getString(R.string.sign_in_errror_field_password_empty_toast)
            return false
        }
        return true
    }

    private fun checkDataSignIn(loginCheck: String, passwordCheck: String): Boolean {
        if (login == loginCheck && password == passwordCheck) {
            isDataInCorrect = true
            return true
        } else if (login == loginCheck && password != passwordCheck
            || login != loginCheck && password == passwordCheck
        ) {
            Toast.makeText(
                activity?.applicationContext,
                getString(R.string.sign_in_errror_incorrect_data_toast),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return false
    }

    private fun checkUser(): Boolean {
        val listUser = (activity as AuthActivity).getListUser()
        val check = (activity as AuthActivity).getFirebasePostService().checkUser(login, password)
        val userOur = listUser.find { it.email == login && it.password == password }
        if (userOur != null) {
            user = User(userOur.id, userOur.email, userOur.password, userOur.name, "")
            isDataInCorrect = checkDataSignIn(user.email, user.password)
            isUserExist = true
        } else return false
        if (isUserExist && isDataInCorrect) {
            return true
        }
        if (check) {
            return true
        }
        return false
    }

    private fun actionButtons() {
        with(signInFragmentBinding) {
            signInButton.setOnClickListener {
                login = loginEditText.text.toString()
                password = passwordEditText.text.toString()
                if (checkFillFields()) {
                    checkUser()
                    signIn()
                }
            }
            signUpButton.setOnClickListener {
                val signUpFragment = SignUpFragment()
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(signUpFragment.javaClass.simpleName)
                    .replace(R.id.auth_activity, signUpFragment)
                    .commit()
            }
        }
    }
}
