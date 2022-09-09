package com.aleca.secretsantacoursework.view

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.database.firebase.GameFirebase
import com.aleca.secretsantacoursework.database.firebase.PairFirebase
import com.aleca.secretsantacoursework.database.firebase.UserFirebase
import com.aleca.secretsantacoursework.database.firebase.UserGameFirebase
import com.aleca.secretsantacoursework.view.ui.home.HomeViewModel

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_profile)
        getUser()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun getUser(): Int {
        return intent.getIntExtra(USER_ID, -1)
    }

    override fun onStop() {
        super.onStop()
        val userFirebaseLogic = UserFirebase()
        userFirebaseLogic.syncUsers(this)

        val gameFirebaseLogic = GameFirebase()
        gameFirebaseLogic.syncUsers(this)

        val userGameFirebase = UserGameFirebase()
        userGameFirebase.syncUsers(this)

        val pairLogic = PairFirebase()
        pairLogic.syncUsers(this)
    }
}