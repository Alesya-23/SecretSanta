package com.aleca.secretsantacoursework.database.storage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.aleca.secretsantacoursework.database.DatabaseHelper
import com.aleca.secretsantacoursework.model.User

private const val TABLE = "user"
private const val COLUMN_ID = "id"
private const val COLUMN_EMAIL = "email"
private const val COLUMN_PASSWORD = "password"
private const val COLUMN_NAME = "name"
private const val COLUMN_HOBBIES = "hobbies"

class UserStorage(context: Context) {
    private var sqlHelper: DatabaseHelper = DatabaseHelper(context)
    private var db = sqlHelper.writableDatabase

    fun open(): UserStorage {
        db = sqlHelper.writableDatabase
        return this
    }

    fun close() {
        db.close()
    }

    @SuppressLint("Range")
    fun getFullList(): List<User?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from $TABLE", null)
        val list: MutableList<User?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val hobbies = cursor.getString(cursor.getColumnIndex(COLUMN_HOBBIES))
            list.add(User(id, email, password, name, hobbies))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    @SuppressLint("Range")
    fun getElement(id: Int): User? {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_ID + " = " + id, null
        )
        if (!cursor.moveToFirst()) {
            return null
        }
        val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
        val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
        val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
        val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        val hobbies = cursor.getString(cursor.getColumnIndex(COLUMN_HOBBIES))
        cursor.close()
        database.close()
        return User(id, email, password, name, hobbies)
    }

    fun insert(model: User) {
        val content = ContentValues()
        content.put(COLUMN_EMAIL, model.name)
        content.put(COLUMN_EMAIL, model.email)
        content.put(COLUMN_PASSWORD, model.password)
        content.put(COLUMN_NAME, model.name)
        content.put(COLUMN_HOBBIES, model.hobbies)
        val database = this.sqlHelper.writableDatabase
        database.insert(TABLE, null, content)
    }

    fun update(model: User) {
        val content = ContentValues()
        content.put(COLUMN_EMAIL, model.name)
        content.put(COLUMN_EMAIL, model.email)
        content.put(COLUMN_PASSWORD, model.password)
        content.put(COLUMN_NAME, model.name)
        content.put(COLUMN_HOBBIES, model.hobbies)
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
