package com.aleca.secretsantacoursework.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, SCHEMA) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS user (\n" +
                    "    id integer PRIMARY KEY AUTOINCREMENT,\n" +
                    "    email character(100) NOT NULL,\n" +
                    "    password character(100) NOT NULL,\n" +
                    "    name character(100) NOT NULL,\n" +
                    "    hobbies character(100) NOT NULL );\n"
        )
        db.execSQL(
            "CREATE TABLE game (\n" +
                    "    id integer PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name character(100) NOT NULL,\n" +
                    "    dateStart  character(100) NOT NULL,\n" +
                    "    dateEnd  character(100) NOT NULL,\n" +
                    "    countGamers character(250) NOT NULL);\n"
        )
        db.execSQL(
            "CREATE TABLE userGame (\n" +
                    "    userId  integer NOT NULL,\n" +
                    "    gameId  integer NOT NULL,\n" +
                    "FOREIGN KEY (userId) REFERENCES user(id),\n" +
                    "FOREIGN KEY (gameId) REFERENCES game(id));\n"
        )
        db.execSQL(
            "CREATE TABLE pair (\n" +
                    "    id integer PRIMARY KEY AUTOINCREMENT,\n" +
                    "    userIdSanta integer NOT NULL,\n" +
                    "    userIdRecipient integer NOT NULL,\n" +
                    "    gameId integer NOT NULL,\n" +
                    "FOREIGN KEY (gameId) REFERENCES game(id));\n"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $DATABASE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "SecretSanta.db" // название бд
        private const val SCHEMA = 1 // версия базы данных
    }
}
