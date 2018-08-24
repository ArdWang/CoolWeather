package com.clw.utils

import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
object PMUtils {
    fun <T> getT(o:Any,i:Int): T? {
        try {
            return ((o.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[i] as
                    Class<T>).newInstance()
        }catch (e:InstantiationException){
            e.printStackTrace()
        }catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }
}