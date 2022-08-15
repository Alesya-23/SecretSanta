package com.aleca.secretsantacoursework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aleca.secretsantacoursework.view.AuthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Handler().postDelayed({
//            val intent = Intent(this, AuthActivity::class.java)
//            startActivity(intent)
//        }, 3000)
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }
}