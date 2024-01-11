package com.github.gunin_igor75.task_list.data.sqlite_db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSqliteHelper(
    private val applicationContext: Context
) : SQLiteOpenHelper(applicationContext, "test.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = applicationContext.assets.open("db_init.sql").bufferedReader().use {
            it.readText()
        }
        sql.split(";")
            .filter { it.isNotBlank() }
            .forEach {
                db?.execSQL(it)
            }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}