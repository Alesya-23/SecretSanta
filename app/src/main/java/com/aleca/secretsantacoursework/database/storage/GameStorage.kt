package com.aleca.secretsantacoursework.database.storage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.aleca.secretsantacoursework.database.DatabaseHelper
import com.aleca.secretsantacoursework.model.Game

private const val TABLE = "game"
private const val COLUMN_ID = "id"
private const val COLUMN_NAME = "name"
private const val COLUMN_DATE_START = "dateStart"
private const val COLUMN_DATE_END = "dateEnd"
private const val COLUMN_COUNT_GAMERS = "countGamers"
private const val COLUMN_STATUS_GAME_IS_ACTIVE = "statusGameIsActive"

class GameStorage(context: Context) {
    private var sqlHelper: DatabaseHelper = DatabaseHelper(context)
    private var db = sqlHelper.writableDatabase

    fun open(): GameStorage {
        db = sqlHelper.writableDatabase
        return this
    }

    fun close() {
        db.close()
    }

    @SuppressLint("Range")
    fun getFullList(): List<Game?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from $TABLE", null)
        val list: MutableList<Game?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val dateStart = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_START))
            val dateEnd = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_END))
            val countGamers = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT_GAMERS))
            val statusGameIsActive = cursor.getInt(cursor.getColumnIndex(
                COLUMN_STATUS_GAME_IS_ACTIVE))
            list.add(Game(id, name, dateStart, dateEnd, countGamers, statusGameIsActive))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range")
    fun getElement(id: Int): Game? {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_ID + " = " + id, null
        )
        if (!cursor.moveToFirst()) {
            return null
        }
        val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        val dateStart = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_START))
        val dateEnd = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_END))
        val countGamers = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT_GAMERS))
        val statusGameIsActive = cursor.getInt(cursor.getColumnIndex(
            COLUMN_STATUS_GAME_IS_ACTIVE))
        cursor.close()
        database.close()
        return Game(id, name, dateStart, dateEnd, countGamers, statusGameIsActive)
    }

    fun insert(model: Game) {
        val content = ContentValues()
        content.put(COLUMN_NAME, model.name)
        content.put(COLUMN_DATE_START, model.dateStart)
        content.put(COLUMN_DATE_END, model.dateEnd)
        content.put(COLUMN_COUNT_GAMERS, model.countGamers)
        content.put(COLUMN_STATUS_GAME_IS_ACTIVE, model.statusGameIsActive)
        val database = this.sqlHelper.writableDatabase
        database.insert(TABLE, null, content)
    }

    fun update(model: Game) {
        val content = ContentValues()
        content.put(COLUMN_NAME, model.name)
        content.put(COLUMN_DATE_START, model.dateStart)
        content.put(COLUMN_DATE_END, model.dateEnd)
        content.put(COLUMN_COUNT_GAMERS, model.countGamers)
        content.put(COLUMN_STATUS_GAME_IS_ACTIVE, model.statusGameIsActive)
        val where = COLUMN_ID + " = " + model.id
        val database = this.sqlHelper.writableDatabase
        database.update(TABLE, content, where, null)
    }

    fun delete(id: Int) {
        val database = this.sqlHelper.writableDatabase
        val where = "$COLUMN_ID = $id"
        database.delete(TABLE, where, null)
    }
}
