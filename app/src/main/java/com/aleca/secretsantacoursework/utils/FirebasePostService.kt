package com.aleca.secretsantacoursework.utils

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.User
import com.google.firebase.database.*

class FirebasePostService : Service() {
    private var binder = ContactBinder()
    private var mBound: Boolean = false

    private var listUsers: ArrayList<User> = ArrayList()

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            binder = service as FirebasePostService.ContactBinder
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    fun getConnection(): ServiceConnection {
        return connection
    }

    fun getBinder(): ContactBinder {
        return binder
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun unBind() {
        mBound = false
    }

    inner class ContactBinder : Binder() {
        fun getService(): FirebasePostService = this@FirebasePostService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private fun getListUsers(): ArrayList<User> {
        var myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        val rootRef = FirebaseDatabase.getInstance().reference.child("user")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val listUser: ArrayList<User> = ArrayList()
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val id =
                            snapshot.child(dataSnapshot.key.toString()).child("id").value.toString()
                        val email = snapshot.child(dataSnapshot.key.toString())
                            .child("email").value.toString()
                        val password = snapshot.child(dataSnapshot.key.toString())
                            .child("password").value.toString()
                        val name = snapshot.child(dataSnapshot.key.toString())
                            .child("name").value.toString()
                        val hobbies = snapshot.child(dataSnapshot.key.toString())
                            .child("hobbies").value.toString()
                        listUser.add(
                            User(
                                id.toInt(),
                                email,
                                password,
                                name,
                                hobbies
                            )
                        )
                    }
                    listUsers = listUser
                }
            })
        return listUsers
    }

    fun getUser(id: Int): User {
        val user = listUsers.find { it.id == id }
        if (user != null) {
            return user
        }
        return User(
            id = -1,
            email = "Not found",
            password = "Not found",
            name = "Not found",
            hobbies = "none"
        )
    }

    fun checkUser(login: String, password: String): Boolean {
        val user = listUsers.find { it.email == login && it.password == password }
        if (user != null) {
            return true
        }
        return false
    }

    fun downloadUserFromFirebase(context: Context): List<User?> {
        var listUserFirebase = ArrayList<User>()
        val userStorage = UserStorage(context)
        userStorage.open()
        val listUserDb = userStorage.getFullList() as ArrayList<User>
        listUserFirebase = getListUsers()
        if (listUserFirebase.isEmpty()) {
            return listUserDb
        } else {
            //проход по бд
            for (item in listUserFirebase) {
                val check = listUserDb.find {
                    it.email == item.email
                            && it.password == item.password
                }
                if (check == null)
                    userStorage.insert(item)
            }
        }
        return listUserDb
    }
}
