package com.aleca.secretsantacoursework.database.storage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.aleca.secretsantacoursework.database.DatabaseHelper
import com.aleca.secretsantacoursework.model.Pair
import com.aleca.secretsantacoursework.view.ui.game.GAME_ID

private const val TABLE = "pair"
private const val COLUMN_ID = "id"
private const val COLUMN_USER_ID_SANTA = "userIdSanta"
private const val COLUMN_USER_ID_RECIPIENT = "userIdRecipient"
private const val COLUMN_GAME_ID = "gameId"

class PairStorage(context: Context) {

    private var sqlHelper: DatabaseHelper = DatabaseHelper(context)
    private var db = sqlHelper.writableDatabase

    fun open(): PairStorage {
        db = sqlHelper.writableDatabase
        return this
    }

    fun close() {
        db.close()
    }

    @SuppressLint("Range", "Recycle")
    fun getFullList(): List<Pair?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from $TABLE", null)
        val list: MutableList<Pair?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val idSanta = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_SANTA))
            val idRecipient = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_RECIPIENT))
            val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
            list.add(Pair(id, idSanta, idRecipient, idGame))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun getFilterList(id: Int): List<Pair?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor =
            database.rawQuery(
                "select * from " + TABLE + " where "
                        + COLUMN_GAME_ID + " = " + id, null
            )
        val list: MutableList<Pair?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val idSanta = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_SANTA))
            val idRecipient = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_RECIPIENT))
            val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
            list.add(Pair(id, idSanta, idRecipient, idGame))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range")
    fun getElement(id: Int): Pair? {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_ID + " = " + id, null
        )
        if (!cursor.moveToFirst()) {
            return null
        }
        val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
        val idSanta = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_SANTA))
        val idRecipient = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_RECIPIENT))
        val idGame = cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID))
        cursor.close()
        database.close()
        return Pair(id, idSanta, idRecipient, idGame)
    }

    fun insert(model: Pair) {
        val content = ContentValues()
        content.put(COLUMN_USER_ID_SANTA, model.userIdSanta)
        content.put(COLUMN_USER_ID_RECIPIENT, model.userIdRecipient)
        content.put(COLUMN_GAME_ID, model.gameId)
        val database = this.sqlHelper.writableDatabase
        database.insert(TABLE, null, content)
    }

    fun update(model: Pair) {
        val content = ContentValues()
        content.put(COLUMN_ID, model.id)
        content.put(COLUMN_USER_ID_SANTA, model.userIdSanta)
        content.put(COLUMN_USER_ID_RECIPIENT, model.userIdRecipient)
        content.put(COLUMN_GAME_ID, model.gameId)
        val where = COLUMN_ID + " = " + model.id
        val database = this.sqlHelper.writableDatabase
        database.update(TABLE, content, where, null)
    }

    fun delete(id: Int) {
        val database = this.sqlHelper.writableDatabase
        val where = "$COLUMN_GAME_ID = $id"
        database.delete(TABLE, where, null)
    }
}
