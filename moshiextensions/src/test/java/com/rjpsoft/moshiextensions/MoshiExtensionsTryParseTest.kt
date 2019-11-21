package com.rjpsoft.moshiextensions

import com.squareup.moshi.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.verify
import okio.Buffer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.EOFException
import java.io.IOException

class MoshiExtensionsTryParseTest {

    @MockK
    private lateinit var moshiMock: Moshi
    @MockK
    private lateinit var adapter: JsonAdapter<TestClass>

    @Before
    fun init() {
        MockKAnnotations.init(this)
        every { moshiMock.adapter<TestClass>(any()) } returns adapter
    }

    //region JsonReader

    @Test
    fun tryParse_WithReaderAndJsonObjectAndParseSuccessful_ShouldReturnCorrectObject() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } returns TestClass("Luke Skywalker", 21)

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNotNull(result)
        assertEquals("Luke Skywalker", result?.name)
        assertEquals(21, result?.age)
    }

    @Test
    fun tryParse_WithReader_ShouldCallAdapterFromJson() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws JsonDataException()

        moshiMock.tryParse<TestClass>(reader)

        verify { moshiMock.adapter(TestClass::class.java) }
        verify { adapter.fromJson(reader) }
    }

    @Test
    fun tryParse_WithReaderAndJsonDataException_ShouldReturnNull() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws JsonDataException()

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    @Test
    fun tryParse_WithReaderAndIOException_ShouldReturnNull() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws IOException()

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    @Test
    fun tryParse_WithReaderAndIllegalArgumentException_ShouldReturnNull() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws IllegalArgumentException()

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    @Test
    fun tryParse_WithReaderAndJsonEncodingException_ShouldReturnNull() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws JsonEncodingException("")

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    @Test
    fun tryParse_WithReaderAndEOFException_ShouldReturnNull() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws EOFException()

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    @Test
    fun tryParse_WithReaderAndIllegalStateException_ShouldReturnNull() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws IllegalStateException()

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    @Test(expected = Exception::class)
    fun tryParse_WithReaderAndException_ShouldThrowException() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        val reader = JsonReader.of(buffer)
        every { adapter.fromJson(any<JsonReader>()) } throws Exception()

        val result = moshiMock.tryParse<TestClass>(reader)

        assertNull(result)
    }

    //endregion

    //region Buffer

    @Test
    fun tryParse_WithBuffer_ShouldCallTryParseWithReader() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        val buffer = Buffer().writeUtf8(jsonObject)
        mockkStatic("com.rjpsoft.moshiextensions.MoshiExtensionsTryParseKt")
        every { moshiMock.tryParse<TestClass>(any<JsonReader>()) } returns TestClass("Luke Skywalker", 21)

        moshiMock.tryParse<TestClass>(buffer)

        verify { moshiMock.adapter(TestClass::class.java) }
        verify { moshiMock.tryParse<TestClass>(any<JsonReader>()) }
    }

    //endregion

    //region String

    @Test
    fun tryParse_WithString_ShouldCallTryParseWithReader() {
        val jsonObject = "{\"name\":\"Luke Skywalker\",\"age\":21}"
        mockkStatic("com.rjpsoft.moshiextensions.MoshiExtensionsTryParseKt")
        every { moshiMock.tryParse<TestClass>(any<JsonReader>()) } returns TestClass("Luke Skywalker", 21)

        moshiMock.tryParse<TestClass>(jsonObject)

        verify { moshiMock.adapter(TestClass::class.java) }
        verify { moshiMock.tryParse<TestClass>(any<JsonReader>()) }
    }

    //endregion

    //region tryParse to JSON

    @Test
    fun tryParse_ToJson_ParseSuccessful_ShouldReturnCorrectObject() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } returns "{\"name\":\"Luke Skywalker\",\"age\":21}"

        val nullableResult = moshiMock.tryParse(myObject)

        assertNotNull(nullableResult)
        assertEquals("{\"name\":\"Luke Skywalker\",\"age\":21}", nullableResult)
    }

    @Test
    fun tryParse_ToJson_ShouldCallAdapterToJson() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws JsonDataException()

        moshiMock.tryParse(myObject)

        verify { moshiMock.adapter(TestClass::class.java) }
        verify { adapter.toJson(myObject) }
    }

    @Test
    fun tryParse_ToJson_WithJsonDataException_ShouldReturnNull() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws JsonDataException()

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    @Test
    fun tryParse_ToJson_IOException_ShouldReturnNull() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws IOException()

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    @Test
    fun tryParse_ToJson_WithReaderAndIllegalArgumentException_ShouldReturnNull() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws IllegalArgumentException()

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    @Test
    fun tryParse_ToJson_WithReaderAndJsonEncodingException_ShouldReturnNull() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws JsonEncodingException("")

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    @Test
    fun tryParse_ToJson_WithReaderAndEOFException_ShouldReturnNull() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws EOFException()

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    @Test
    fun tryParse_ToJson_WithReaderAndIllegalStateException_ShouldReturnNull() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws IllegalStateException()

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    @Test(expected = Exception::class)
    fun tryParse_ToJson_WithReaderAndException_ShouldThrowException() {
        val myObject = TestClass("Luke Skywalker", 21)
        every { adapter.toJson(any()) } throws Exception()

        val result = moshiMock.tryParse(myObject)

        assertNull(result)
    }

    //endregion
}