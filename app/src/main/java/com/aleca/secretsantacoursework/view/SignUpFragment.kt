package com.aleca.secretsantacoursework.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.databinding.SignUpFragmentBinding
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.viewmodel.UserViewModel
import java.util.*
import kotlin.concurrent.schedule

class SignUpFragment : Fragment(R.layout.sign_up_fragment) {
    private lateinit var signUpFragmentBinding: SignUpFragmentBinding
    private var name: String = ""
    private var login: String = ""
    private var password: String = ""
    private var repeatPassword: String = ""
    private var isEdit: Boolean = false
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpFragmentBinding = SignUpFragmentBinding.bind(view)
        actionSignUpButton()
    }

    private fun signUp() {
        with(signUpFragmentBinding) {
            name = loginSignUpEditText.text.toString()
            login = emailSignUp.text.toString()
            password = passwordSignUpEditText.text.toString()
            repeatPassword = passwordSignUpRepeatEditText.text.toString()
            if (checkFillFields()) {
                    registerUser()
                val signInFragment = SignInFragment()
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(signInFragment.toString())
                    .replace(R.id.auth_activity, signInFragment)
                    .commit()
            }
        }
    }

    private fun registerUser() {
        val context = (activity as AuthActivity).applicationContext
        val listUser = (activity as AuthActivity).getFirebasePostService().syncFirebaseAndLocalDataUser((activity as AuthActivity).applicationContext)
        val userOur =
            listUser.find { it?.email == login && it.password == password || it?.email == login }
        if (userOur != null) {
            isEdit = true
            Toast.makeText(
                context,
                getString(R.string.sign_up_error_user_exsist_toast),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (!isEdit) {
            userViewModel.addUser(
                User(
                    0,
                    login,
                    password,
                    name,
                    getString(R.string.write_your_hobbies)
                ), context
            )
            Toast.makeText(
                context,
                getString(R.string.sign_up_registration_sucsess_toast),
                Toast.LENGTH_LONG
            ).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun actionSignUpButton() {
        signUpFragmentBinding.signUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun checkFillFields(): Boolean {
        with(signUpFragmentBinding) {
            if (name.isEmpty()) {
                loginSignUpLayout.hint = getString(R.string.dont_fill_field_name_hint)
                return false
            }
            if (login.isEmpty()) {
                loginSignUpLayout.hint = getString(R.string.dont_fill_field_email_hint)
                return false
            }
            if (!login.matches(Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"))) {
                emailSignUpLayout.hint = getString(R.string.dont_fill_correct_field_email_hint)
                return false
            }
            if (password.isEmpty()) {
                emailSignUpLayout.hint = getString(R.string.dont_fill_field_password_hint)
                return false
            }
            if (repeatPassword.isEmpty() || repeatPassword != password) {
                passwordSignUpRepeatLayout.hint =
                    getString(R.string.dont_equals_repeat_password_hint)
                return false
            }
            if (!agreeWithRulesCheckbox.isChecked) {
                agreeWithRulesCheckbox.setTextColor(Color.RED)
            } else {
                agreeWithRulesCheckbox.setTextColor(Color.BLACK)
            }
            if (agreeGetNewsEmailCheckbox.isChecked) {
                agreeGetNewsEmailCheckbox.setTextColor(Color.RED)
            } else {
                agreeGetNewsEmailCheckbox.setTextColor(Color.BLACK)
            }
            return true
        }
    }
}
