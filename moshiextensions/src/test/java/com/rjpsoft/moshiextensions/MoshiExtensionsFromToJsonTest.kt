package com.rjpsoft.moshiextensions

import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Buffer
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MoshiExtensionsFromToJsonTest {

    private lateinit var moshi: Moshi

    @Before
    fun init() {
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Test
    fun toJson_Object_ShouldReturnCorrectString() {
        val value = TestClass("Darth Vader", 45)

        val result = moshi.toJson(value)

        Assert.assertEquals("{\"name\":\"Darth Vader\",\"age\":45}", result)
    }

    @Test
    fun toJson_ListObjects_ShouldReturnCorrectString() {
        val value = listOf(
            TestClass("Darth Vader", 45),
            TestClass("Luke Skywalker", 21)
        )

        val result = moshi.toJson(value)

        Assert.assertEquals(
            "[{\"name\":\"Darth Vader\",\"age\":45},{\"name\":\"Luke Skywalker\",\"age\":21}]",
            result
        )
    }

    @Test
    fun fromJson_WithReaderAndJsonObject_ShouldReturnCorrectObject() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)

        val result = moshi.fromJson<TestClass>(reader)

        Assert.assertEquals("Luke Skywalker", result.name)
        Assert.assertEquals(21, result.age)

    }

    @Test
    fun fromJson_WithReaderAndJsonListObject_ShouldReturnCorrectObject() {
        val jsonObject = "[{\"name\":\"Darth Vader\",\"age\":45},{\"name\":\"Luke Skywalker\",\"age\":21}]"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)

        val result = moshi.fromJson<List<TestClass>>(reader)

        Assert.assertEquals(2, result.size)
        Assert.assertEquals("Darth Vader", result[0].name)
        Assert.assertEquals(45, result[0].age)
        Assert.assertEquals("Luke Skywalker", result[1].name)
        Assert.assertEquals(21, result[1].age)

    }

    @Test
    fun fromJson_WithBufferAndJsonObject_ShouldReturnCorrectObject() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)

        val result = moshi.fromJson<TestClass>(buffer)

        Assert.assertEquals("Luke Skywalker", result.name)
        Assert.assertEquals(21, result.age)

    }

    @Test
    fun fromJson_WithBufferAndJsonListObject_ShouldReturnCorrectObject() {
        val jsonObject = "[{\"name\":\"Darth Vader\",\"age\":45},{\"name\":\"Luke Skywalker\",\"age\":21}]"
        val buffer = Buffer().writeUtf8(jsonObject)

        val result = moshi.fromJson<List<TestClass>>(buffer)

        Assert.assertEquals(2, result.size)
        Assert.assertEquals("Darth Vader", result[0].name)
        Assert.assertEquals(45, result[0].age)
        Assert.assertEquals("Luke Skywalker", result[1].name)
        Assert.assertEquals(21, result[1].age)

    }

    @Test
    fun fromJson_WithStringJsonObject_ShouldReturnCorrectObject() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"

        val result = moshi.fromJson<TestClass>(jsonObject)

        Assert.assertEquals("Luke Skywalker", result.name)
        Assert.assertEquals(21, result.age)

    }

    @Test
    fun fromJson_WithStringJsonListObject_ShouldReturnCorrectObject() {
        val jsonObject = "[{\"name\":\"Darth Vader\",\"age\":45},{\"name\":\"Luke Skywalker\",\"age\":21}]"

        val result = moshi.fromJson<List<TestClass>>(jsonObject)

        Assert.assertEquals(2, result.size)
        Assert.assertEquals("Darth Vader", result[0].name)
        Assert.assertEquals(45, result[0].age)
        Assert.assertEquals("Luke Skywalker", result[1].name)
        Assert.assertEquals(21, result[1].age)

    }
}