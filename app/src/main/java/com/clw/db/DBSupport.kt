package com.clw.db

import android.annotation.SuppressLint
import android.content.ContentValues
import com.clw.common.MainApplication.Companion.context

@SuppressLint("Recycle")
class DBSupport {

    private var dbHelper:DBHelper?=null

    //private val db: SQLiteDatabase

    companion object {
        const val DB_NAME  = "weather_data"
        const val VERSION = 1
    }

    init {
        dbHelper = DBHelper(context, DB_NAME, null, VERSION)
    }

    /**
     * 存储省级的数据
     * 为了安全起见存储完毕需要关闭
     */
    fun saveProvince(province: Province){
        val db = dbHelper!!.writableDatabase
        val values = ContentValues()
        values.put("provinceName",province.provinceName)
        values.put("provinceCode",province.provinceCode)
        db.insert("Province",null,values)
        values.clear()
        db.close()
    }

    /**
     * 存储市级
     */
    fun saveCity(city: City){
        val db = dbHelper!!.writableDatabase
        val values = ContentValues()
        values.put("provinceId",city.provinceId)
        values.put("cityName",city.cityName)
        values.put("cityCode",city.cityCode)
        db.insert("City",null,values)
        values.clear()
        db.close()
    }

    /**
     * 存储县级
     */
    fun saveCounty(county: County){
        val db = dbHelper!!.writableDatabase
        val values = ContentValues()
        values.put("cityId",county.cityId)
        values.put("countyName",county.countyName)
        values.put("weatherId",county.weatherId)
        db.insert("County",null,values)
        values.clear()
        db.close()
    }

    /**
     * 查询省级数据
     */
    fun findProvince():MutableList<Province>{
        val db = dbHelper!!.writableDatabase
        val list  = ArrayList<Province>()
        val sql = ("select * from Province")
        val cursor = db.rawQuery(sql, null)
        if(cursor.moveToFirst()){
            do {
                val province = Province()
                province.id = cursor.getInt(cursor.getColumnIndex("_id"))
                province.provinceCode = cursor.getInt(cursor.getColumnIndex("provinceCode"))
                province.provinceName = cursor.getString(cursor.getColumnIndex("provinceName"))
                list.add(province)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    /**
     * 查询市级数据
     */
    fun findCity(proviceId:Int):MutableList<City>{
        val db = dbHelper!!.writableDatabase
        val list = ArrayList<City>()
        val sql = ("select * from City where provinceId=?")
        val cursor = db.rawQuery(sql, arrayOf(proviceId.toString()))
        if(cursor.moveToNext()){
            do {
                val city = City()
                city.id = cursor.getInt(cursor.getColumnIndex("_id"))
                city.provinceId = cursor.getInt(cursor.getColumnIndex("provinceId"))
                city.cityCode = cursor.getInt(cursor.getColumnIndex("cityCode"))
                city.cityName = cursor.getString(cursor.getColumnIndex("cityName"))
                list.add(city)

            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }


    /**
     * 查询县级数据
     */
    fun findCounty(cityId:Int):MutableList<County>{
        val db = dbHelper!!.writableDatabase
        val list = ArrayList<County>()
        val sql = ("select * from County where cityId=?")
        val cursor = db.rawQuery(sql, arrayOf(cityId.toString()))

        if(cursor.moveToNext()){
            do{
                val county = County()
                county.id = cursor.getInt(cursor.getColumnIndex("_id"))
                county.cityId = cursor.getInt(cursor.getColumnIndex("cityId"))
                county.countyName = cursor.getString(cursor.getColumnIndex("countyName"))
                county.weatherId = cursor.getString(cursor.getColumnIndex("weatherId"))
                list.add(county)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }





}