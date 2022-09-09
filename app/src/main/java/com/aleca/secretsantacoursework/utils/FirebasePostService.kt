package com.aleca.secretsantacoursework.utils

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.aleca.secretsantacoursework.database.storage.GameStorage
import com.aleca.secretsantacoursework.database.storage.PairStorage
import com.aleca.secretsantacoursework.database.storage.UserGameStorage
import com.aleca.secretsantacoursework.database.storage.UserStorage
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.model.UserGame
import com.aleca.secretsantacoursework.model.Pair
import com.google.firebase.database.*

class FirebasePostService : Service() {
    private var binder = ContactBinder()
    private var mBound: Boolean = false

    private var listUsers: ArrayList<User> = ArrayList()
    private var listGames: ArrayList<Game> = ArrayList()
    private var listPairs: ArrayList<Pair> = ArrayList()
    private var listUserGames: ArrayList<UserGame> = ArrayList()

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
                        listUsers.add(
                            User(
                                id.toInt(),
                                email,
                                password,
                                name,
                                hobbies
                            )
                        )
                    }
                }
            })
        return listUsers
    }

    private fun getListGames(): ArrayList<Game> {
        var myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        val rootRef = FirebaseDatabase.getInstance().reference.child("game")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val id =
                            snapshot.child(dataSnapshot.key.toString()).child("id").value.toString()
                        val dateStart = snapshot.child(dataSnapshot.key.toString())
                            .child("dateStart").value.toString()
                        val dateEnd = snapshot.child(dataSnapshot.key.toString())
                            .child("dateEnd").value.toString()
                        val name = snapshot.child(dataSnapshot.key.toString())
                            .child("name").value.toString()
                        val countGamers = snapshot.child(dataSnapshot.key.toString())
                            .child("countGamers").value.toString()
                        val statusGameIsActive = snapshot.child(dataSnapshot.key.toString())
                            .child("statusGameIsActive").value.toString()
                        listGames.add(
                            Game(
                                id.toInt(),
                                name,
                                dateStart,
                                dateEnd,
                                countGamers.toInt(),
                                statusGameIsActive.toInt()
                            )
                        )
                    }
                }
            })
        return listGames
    }


    private fun getListPairs(): ArrayList<Pair> {
        var myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        val rootRef = FirebaseDatabase.getInstance().reference.child("pair")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val id =
                            snapshot.child(dataSnapshot.key.toString()).child("id").value.toString()
                        val gameId = snapshot.child(dataSnapshot.key.toString())
                            .child("gameId").value.toString()
                        val userIdRecipient = snapshot.child(dataSnapshot.key.toString())
                            .child("userIdRecipient").value.toString()
                        val userIdSanta = snapshot.child(dataSnapshot.key.toString())
                            .child("userIdSanta").value.toString()
                        listPairs.add(
                            Pair(
                                id.toInt(),
                                userIdSanta.toInt(),
                                userIdRecipient.toInt(),
                                gameId.toInt(),
                            )
                        )
                    }
                }
            })
        return listPairs
    }

    private fun getListUserGames(): ArrayList<UserGame> {
        var myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        val rootRef = FirebaseDatabase.getInstance().reference.child("userGame")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val gameId = snapshot.child(dataSnapshot.key.toString())
                            .child("gameId").value.toString()
                        val userId = snapshot.child(dataSnapshot.key.toString())
                            .child("userId").value.toString()
                        listUserGames.add(
                            UserGame(
                                userId.toInt(),
                                gameId.toInt()
                            )
                        )
                    }
                }
            })
        return listUserGames
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

    fun syncFirebaseAndLocalDataUser(context: Context): List<User?> {
        var listUserFirebase = ArrayList<User>()
        val userStorage = UserStorage(context)
        userStorage.open()
        var listUserDb = userStorage.getFullList() as ArrayList<User>
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
        listUserDb = userStorage.getFullList() as ArrayList<User>
        return listUserDb
    }

    fun syncFirebaseAndLocalDataGame(context: Context): List<Game?> {
        var listGameFirebase = ArrayList<Game>()
        val gameStorage = GameStorage(context)
        gameStorage.open()
        var listGameDb = gameStorage.getFullList() as ArrayList<Game>
        listGameFirebase = getListGames()
        if (listGameFirebase.isEmpty()) {
            return listGameDb
        } else {
            //проход по бд
            for (item in listGameFirebase) {
                val check = listGameDb.find {
                   it.name == item.name && it.dateStart ==  item.dateStart && it.dateEnd == item.dateEnd
                }
                if (check == null)
                    gameStorage.insert(item)
            }
        }
        listGameDb = gameStorage.getFullList() as ArrayList<Game>
        gameStorage.close()
        return listGameDb
    }

    fun syncFirebaseAndLocalDataPair(context: Context): ArrayList<Pair> {
        var listPairFirebase = ArrayList<Pair>()
        val pairStorage = PairStorage(context)
        pairStorage.open()
        var listPairDb = pairStorage.getFullList() as ArrayList<Pair>
        listPairFirebase = getListPairs()
        if (listPairFirebase.isEmpty()) {
            return listPairDb
        } else {
            //проход по бд
            for (item in listPairFirebase) {
                val check = listPairDb.find {
                  it.gameId == item.gameId
                }
                if (check == null)
                    pairStorage.insert(item)
            }
        }
        listPairDb = pairStorage.getFullList() as ArrayList<Pair>
        pairStorage.close()
        return listPairDb
    }

    fun syncFirebaseAndLocalDataUserGame(context: Context): ArrayList<UserGame> {
        var listUserGameFirebase = ArrayList<UserGame>()
        val userGameStorage = UserGameStorage(context)
        userGameStorage.open()
        var listUserGameDb = userGameStorage.getFullList() as ArrayList<UserGame>
        listUserGameFirebase = getListUserGames()
        if (listUserGameFirebase.isEmpty()) {
            return listUserGameDb
        } else {
            //проход по бд
            for (item in listUserGameFirebase) {
                val check = listUserGameDb.find {
                    it.gameId == item.gameId
                }
                if (check == null)
                    userGameStorage.insert(item)
            }
        }
        listUserGameDb = userGameStorage.getFullList() as ArrayList<UserGame>
        userGameStorage.close()
        return listUserGameDb
    }
}
