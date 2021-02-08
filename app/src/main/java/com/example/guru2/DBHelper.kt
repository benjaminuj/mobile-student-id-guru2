package com.example.guru2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Blob

class DBHelper(context: Context)
    :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_NAME = "RecordDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "entry"
        private val DATE = "date"
        private val TIME = "time"
        private val NAME = "name"
        private val ID = "id"
        private val MAJOR = "major"
        private val SPOT = "spot"
        private val LECTURE = "lecture"
        private val PROFESSOR = "professor"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable_entry =
            "CREATE TABLE $TABLE_NAME" +
                    "($DATE TEXT," + "$TIME TEXT," +
                    "$NAME TEXT," + "$ID INTEGER," + "$MAJOR TEXT," +
                    "$SPOT TEXT," + "$LECTURE TEXT," + "$PROFESSOR TEXT)"

        db?.execSQL(createTable_entry)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}

