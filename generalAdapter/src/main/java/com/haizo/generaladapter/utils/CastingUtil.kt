package com.haizo.generaladapter.utils

object CastingUtil {

    @Suppress("UNCHECKED_CAST")
    fun <T> genericCastOrNull(anything: Any?, clazz: Class<T>?): T? {
        return if (clazz?.isInstance(anything) == true) anything as T else null
    }
}