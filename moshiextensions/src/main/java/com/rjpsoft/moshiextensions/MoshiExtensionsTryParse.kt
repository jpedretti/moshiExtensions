package com.rjpsoft.moshiextensions

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okio.Buffer
import okio.BufferedSource
import java.io.EOFException
import java.io.IOException

/**
 * Tries to create a object of type [T] from the given [json] converting it to a utf8 buffer.
 * If one of the following exceptions occur the return will be null.
 * [JsonDataException, IOException, IllegalArgumentException, JsonEncodingException, EOFException, IllegalStateException]
 * @receiver Moshi
 * @param json String
 * @return T?
 */
inline fun <reified T> Moshi.tryParse(json: String): T? = tryParse(Buffer().writeUtf8(json))

/**
 * Tries to create a object of type [T] from the given [source].
 * If one of the following exceptions occur the return will be null.
 * [JsonDataException, IOException, IllegalArgumentException, JsonEncodingException, EOFException, IllegalStateException]
 * @receiver Moshi
 * @param source String
 * @return T?
 */
inline fun <reified T> Moshi.tryParse(source: BufferedSource): T? = tryParse(JsonReader.of(source))

/**
 * Tries to create a object of type [T] from the given [reader].
 * If one of the following exceptions occur the return will be null.
 * [JsonDataException, IOException, IllegalArgumentException, JsonEncodingException, EOFException, IllegalStateException]
 * @receiver Moshi
 * @param reader String
 * @return T?
 */
inline fun <reified T> Moshi.tryParse(reader: JsonReader): T? = try {
    fromJson(reader)
} catch (jde: JsonDataException) {
    null
} catch (ioe: IOException) {
    null
} catch (iae: IllegalArgumentException) {
    null
} catch (jee: JsonEncodingException) {
    null
} catch (eoe: EOFException) {
    null
} catch (ise: IllegalStateException) {
    null
}
