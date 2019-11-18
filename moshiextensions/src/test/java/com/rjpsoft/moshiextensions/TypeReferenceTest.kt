package com.rjpsoft.moshiextensions

import org.junit.Test

import org.junit.Assert.*

class TypeReferenceTest {

    @Test
    fun getType_WithParameterizedType_ShouldReturnNotNullType() {
        val typeReference = object : TypeReference<String>() {}

        val type = typeReference.type

        assertNotNull(type)
    }

    @Test
    fun getType_WithParameterizedType_ShouldReturnCorrectType() {
        val typeReference = object : TypeReference<List<Int>>() {}

        val type = typeReference.type

        assertEquals("java.util.List<? extends java.lang.Integer>", type?.typeName)
    }

    @Test
    fun getType_WithNotParameterizedType_ShouldReturnNull() {
        val typeReference = object : TypeReference<Nothing>() {}

        val type = typeReference.type

        assertNull(type)
    }
}
