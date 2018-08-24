package com.clw.base.rx

import io.reactivex.functions.Function

/**
 * 通用数据类型转换
 */
open class BaseFunction<T>:Function<T,T> {
    override fun apply(t: T): T {
        return t
    }
}