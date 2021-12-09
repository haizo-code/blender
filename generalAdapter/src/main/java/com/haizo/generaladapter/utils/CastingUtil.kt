package com.haizo.generaladapter.utils

object CastingUtil {

    @Suppress("UNCHECKED_CAST")
    fun <T> castOrNull(instance: Any?, toClass: Class<T>?): T? {
        return if (toClass?.isInstance(instance) == true) instance as T else null
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> castOrNull(instance: Any?): T? {
        return instance as? T
    }
}