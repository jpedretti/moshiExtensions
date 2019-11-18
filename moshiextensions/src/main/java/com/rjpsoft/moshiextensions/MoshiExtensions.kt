package com.rjpsoft.moshiextensions

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType

/***
 * Gets the adapter of the [T] type
 * @receiver Moshi
 * @return JsonAdapter<T>
 */
inline fun <reified T> Moshi.getAdapter(): JsonAdapter<T> {
    val typeRef = object : TypeReference<T>() {}.type
    val type =
        if (typeRef is ParameterizedType) typeRef.actualTypeArguments.first() else T::class.java
    return when (type) {
        T::class.java -> adapter<T>(T::class.java)
        else -> {
            val realType = if (type is WildcardType) type.upperBounds.first() else type
            val adapterType = Types.newParameterizedType(T::class.java, realType)
            adapter<T>(adapterType)
        }
    }
}
