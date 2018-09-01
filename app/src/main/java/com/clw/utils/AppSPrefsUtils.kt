package com.clw.utils


import android.content.Context
import android.content.SharedPreferences
import com.clw.common.MainApplication.Companion.context
import com.clw.common.ProviderConstant
import android.content.SharedPreferences.Editor

/**
 * 存储数据类型
 */
object AppSPrefsUtils {

    private var sp: SharedPreferences = context.getSharedPreferences(ProviderConstant.KEY_WEATHER, Context.MODE_PRIVATE)
    private var ed: Editor

    init {
        ed = sp.edit()
    }

    /**
        Boolean数据
     */
    fun putBoolean(key: String, value: Boolean) {
        ed.putBoolean(key, value)
        ed.apply()
    }

    /**
        默认 false
     */
    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    /**
        String数据
     */
    fun putString(key: String, value: String) {
        ed.putString(key, value)
        ed.apply()
    }

    /**
        默认 ""
     */
    fun getString(key: String): String {
        return sp.getString(key, "")
    }

    /**
        Int数据
     */
    fun putInt(key: String, value: Int) {
        ed.putInt(key, value)
        ed.apply()
    }

    /**
        默认 0
     */
    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    /**
        Long数据
     */
    fun putLong(key: String, value: Long) {
        ed.putLong(key, value)
        ed.apply()
    }

    /**
        默认 0
     */
    fun getLong(key: String): Long {
        return sp.getLong(key, 0)
    }

    /**
        Set数据
     */
    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key).toMutableSet()
        localSet.addAll(set)
        ed.putStringSet(key, localSet)
        ed.apply()
    }

    /**
        默认空set
     */
    fun getStringSet(key: String): Set<String> {
        val set = setOf<String>()
        return sp.getStringSet(key, set)
    }

    /**
        删除key数据
     */
    fun remove(key: String) {
        ed.remove(key)
        ed.apply()
    }
}