package com.aleca.secretsantacoursework.database.storage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.aleca.secretsantacoursework.database.DatabaseHelper
import com.aleca.secretsantacoursework.model.UserGame

private const val TABLE = "userGame"
private const val COLUMN_ID = "id"
private const val COLUMN_USER_ID = "userId"
private const val COLUMN_GAME_ID = "gameId"

class UserGameStorage(context: Context) {

    private var sqlHelper: DatabaseHelper = DatabaseHelper(context)
    private var db = sqlHelper.writableDatabase

    fun open(): UserGameStorage {
        db = sqlHelper.writableDatabase
        return this
    }

    fun close() {
        db.close()
    }

    @SuppressLint("Range")
    fun getFullList(): List<UserGame?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from $TABLE", null)
        val list: MutableList<UserGame?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val idUser = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
            val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
            list.add(UserGame(idUser, idGame))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range")
    fun getFilterList(idGame: Int): List<UserGame?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_GAME_ID + " = " + idGame, null
        )
        val list: MutableList<UserGame?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val idUser = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
            val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
            list.add(UserGame(idUser, idGame))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range")
    fun getFilterListGames(idUser: Int): List<UserGame?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_USER_ID + " = " + idUser, null
        )
        val list: MutableList<UserGame?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
            val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
            list.add(UserGame(userId, idGame))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range")
    fun getElement(id: Int): UserGame? {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_ID + " = " + id, null
        )
        if (!cursor.moveToFirst()) {
            return null
        }
        val idUser = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
        val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
        cursor.close()
        database.close()
        return UserGame(idUser, idGame)
    }

    fun insert(model: UserGame) {
        val content = ContentValues()
        content.put(COLUMN_USER_ID, model.userId)
        content.put(COLUMN_GAME_ID, model.gameId)
        val database = this.sqlHelper.writableDatabase
        database.insert(TABLE, null, content)
    }

    fun update(model: UserGame) {
        val content = ContentValues()
        content.put(COLUMN_USER_ID, model.userId)
        content.put(COLUMN_GAME_ID, model.gameId)
        val where = COLUMN_USER_ID + " = " + model.userId
        val database = this.sqlHelper.writableDatabase
        database.update(TABLE, content, where, null)
    }

    fun delete(userId: Int, gameId: Int) {
        val database = this.sqlHelper.writableDatabase
        val where = "$COLUMN_USER_ID = $userId AND $COLUMN_GAME_ID = $gameId"
        database.delete(TABLE, where, null)
    }
}
