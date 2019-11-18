package com.rjpsoft.moshiextensions

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class MoshiExtensionsTest {

    @MockK
    private lateinit var moshiMock: Moshi
    @MockK
    private lateinit var intAdapter: JsonAdapter<Integer>
    @MockK
    private lateinit var listAdapter: JsonAdapter<java.util.List<Integer>>
    @MockK
    private lateinit var wildCardAdapter: JsonAdapter<java.util.List<*>>



    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getAdapter_WithInteger_ShouldCallMoshiAdapterWithIntegerType() {
        every { moshiMock.adapter<Integer>(Integer::class.java) } returns intAdapter

        moshiMock.getAdapter<Integer>()

        verify { moshiMock.adapter<Integer>(Integer::class.java) }
    }

    @Test
    fun getAdapter_WithInteger_ShouldReturnAdapterWithIntegerType() {
        every { moshiMock.adapter<Integer>(Integer::class.java) } returns intAdapter

        val result = moshiMock.getAdapter<Integer>()

        assertEquals(intAdapter, result)
    }

    @Test
    fun getAdapter_WithListInteger_ShouldCallMoshiAdapterWithListIntegerType() {
        val adapterType = getAdapterType<java.util.List<Integer>>()
        every { moshiMock.adapter<java.util.List<Integer>>(adapterType) } returns listAdapter

        moshiMock.getAdapter<java.util.List<Integer>>()

        verify { moshiMock.adapter<java.util.List<Integer>>(adapterType) }
    }

    @Test
    fun getAdapter_WithListInteger_ShouldCallReturnListIntegerAdapter() {
        val adapterType = getAdapterType<java.util.List<Integer>>()
        every { moshiMock.adapter<java.util.List<Integer>>(adapterType) } returns listAdapter

        val result = moshiMock.getAdapter<java.util.List<Integer>>()

        assertEquals(listAdapter, result)
    }

    @Test
    fun getAdapter_WithListWildCard_ShouldCallMoshiAdapterWithListIntegerType() {
        val adapterType = getWildCardAdapterType<java.util.List<*>>()
        every { moshiMock.adapter<java.util.List<*>>(adapterType) } returns wildCardAdapter

        moshiMock.getAdapter<java.util.List<*>>()

        verify { moshiMock.adapter<java.util.List<*>>(adapterType) }
    }

    @Test
    fun getAdapter_WithListWildCard_ShouldCallReturnListIntegerAdapter() {
        val adapterType = getWildCardAdapterType<java.util.List<*>>()
        every { moshiMock.adapter<java.util.List<*>>(adapterType) } returns wildCardAdapter

        val result = moshiMock.getAdapter<java.util.List<*>>()

        assertEquals(wildCardAdapter, result)
    }

    private inline fun <reified T> getAdapterType(): ParameterizedType {
        val typeReference = object : TypeReference<T>() {}.type
        val type = (typeReference as ParameterizedType).actualTypeArguments.first()
        return Types.newParameterizedType(T::class.java, type)
    }

    private inline fun <reified T> getWildCardAdapterType(): ParameterizedType {
        val typeReference = object : TypeReference<T>() {}.type
        val type = (typeReference as ParameterizedType).actualTypeArguments.first()
        return Types.newParameterizedType(T::class.java, (type as WildcardType).upperBounds.first())
    }
}