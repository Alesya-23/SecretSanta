package com.aleca.secretsantacoursework

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.aleca.secretsantacoursework.database.firebase.GameFirebase
import com.aleca.secretsantacoursework.database.firebase.PairFirebase
import com.aleca.secretsantacoursework.database.firebase.UserFirebase
import com.aleca.secretsantacoursework.database.firebase.UserGameFirebase
import com.aleca.secretsantacoursework.view.AuthActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }, 3000)
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