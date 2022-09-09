package com.aleca.secretsantacoursework.view

import com.aleca.secretsantacoursework.database.firebase.PairFirebase
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.database.firebase.GameFirebase
import com.aleca.secretsantacoursework.database.firebase.UserFirebase
import com.aleca.secretsantacoursework.database.firebase.UserGameFirebase
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.Pair
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.model.UserGame
import com.aleca.secretsantacoursework.utils.FirebasePostService
import kotlinx.coroutines.*

class AuthActivity : AppCompatActivity() {
    private var mService: FirebasePostService = FirebasePostService()
    private var listUsers = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        CoroutineScope(Dispatchers.IO).launch {
            listUsers = mService.syncFirebaseAndLocalDataUser(this@AuthActivity) as ArrayList<User>
            val listGames = mService.syncFirebaseAndLocalDataGame(this@AuthActivity) as ArrayList<Game>
            val listPairs = mService.syncFirebaseAndLocalDataPair(this@AuthActivity) as ArrayList<Pair>
            val listUserGame = mService.syncFirebaseAndLocalDataUserGame(this@AuthActivity) as ArrayList<UserGame>
            withContext(Dispatchers.Main) {
                if (savedInstanceState == null) {
                    val signInFragment = SignInFragment()
                    supportFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.auth_activity, signInFragment).commit()
                }
            }
        }
//        if (savedInstanceState == null) {
//            val signInFragment = SignInFragment()
//            supportFragmentManager.beginTransaction().addToBackStack(null)
//                .replace(R.id.auth_activity, signInFragment).commit()
//        }
    }

    override fun onStart() {
        super.onStart()
        mService = mService.getBinder().getService()
        Intent(this, FirebasePostService::class.java).also { intent ->
            bindService(intent, mService.getConnection(), Context.BIND_AUTO_CREATE)
        }
        startService(Intent(this, FirebasePostService::class.java))
    }

    override fun onStop() {
        super.onStop()
        unbindService(mService.getConnection())
        mService.unBind()
    }

    fun getFirebasePostService(): FirebasePostService {
        return mService
    }
}
