package com.rjpsoft.moshiextensions

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/***
 * Class that helps getting the type of the generic parameter
 * @param T
 * @property type Type?
 */
abstract class TypeReference<T> : Comparable<TypeReference<T>> {

    /**
     * Gets the generic super class type.
     */
    val type: Type? =
        (javaClass.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.first()

    override fun compareTo(other: TypeReference<T>) = if (other.type == type) 0 else -1
}