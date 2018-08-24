package com.clw.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory

/**
 * SQLite创建表帮助类
 */
class DBHelper(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_PROVINCE)  //创建省表
        db.execSQL(CREATE_CITY)
        db.execSQL(CREATE_COUNTY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        /**
         * 创建省份表
         */
       const val CREATE_PROVINCE = ("create table Province (_id integer primary key autoincrement,"
                + "provinceName varchar(50),provinceCode integer)")

        /**
         * 创建市级表
         */
        const val CREATE_CITY = ("create table City (_id integer primary key autoincrement," +
                "provinceId integer,cityName varchar(50),cityCode integer)")

        /**
         * 创建县级表
         */
        const val CREATE_COUNTY = ("create table County (_id integer primary key autoincrement," +
                "cityId integer,countyName varchar(50),weatherId varchar(50))")
    }

}
