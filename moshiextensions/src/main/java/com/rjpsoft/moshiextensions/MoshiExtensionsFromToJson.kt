package com.rjpsoft.moshiextensions

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okio.Buffer
import okio.BufferedSource

/***
 * Creates a object of type [T] from the given [json] converting it to a utf8 buffer
 * @receiver Moshi
 * @param json String
 * @return T
 */
inline fun <reified T> Moshi.fromJson(json: String): T = fromJson(Buffer().writeUtf8(json))

/***
 *  Creates a object of type [T] fom the given [source]
 * @receiver Moshi
 * @param source BufferedSource
 * @return T
 */
inline fun <reified T> Moshi.fromJson(source: BufferedSource): T = fromJson(JsonReader.of(source))

/***
 * Creates a object of type [T] from the given [reader]
 * @receiver Moshi
 * @param reader JsonReader
 * @return T
 */
inline fun <reified T> Moshi.fromJson(reader: JsonReader): T =
    getAdapter<T>().fromJson(reader) ?: throw JsonDataException()

/***
 * Transform the [value] to json string
 * @receiver Moshi
 * @param value T
 * @return String
 */
inline fun <reified T> Moshi.toJson(value: T): String = getAdapter<T>().toJson(value)
