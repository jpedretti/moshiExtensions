# Moshi Extensions

#### A set of extension functions to make easier to serialize and deserialize objects using [Moshi](https://github.com/square/moshi).

Moshi is a very nice library that helps us to work with JSON, but yet we always have to get the right adapter when using it to manually serialize/deserialize JSON.

This set of extensions helps us to write fewer and more readable code.

### Serializing Objects

To serialize objects just call one method ```toJson```:
```kotlin
val moshi = Moshi.Builder().build()
val myEntity = Entity()

val jsonEntity = moshi.toJson(myEntity)
```  

### Deserializing JSON to Object

To deserialize JSON just call one method ```fromJson```
```kotlin
data class MyClass(val name: String, val age: Int)

val moshi = Moshi.Builder().build()
val myJson = "{\"name\":\"Darth Vader\",\"age\":45}"

val myInstance = moshi.fromJson<MyClass>(myJson)
```

It works with a more complex scenario too:
```kotlin
data class MyClass(val name: String, val age: Int)

val moshi = Moshi.Builder().build()
val myJson = "[{\"name\":\"Darth Vader\",\"age\":45},{\"name\":\"Luke Skywalker\",\"age\":21}]"

val myInstance = moshi.fromJson<List<MyClass>>(myJson)
```

The ```fromJson``` method works with ```String```, ```BufferedSource``` and ```JsonReader``` parameters.

### Handling Exception

The ```toJson``` and ```fromJson``` methods **returns not null** results but, they **don't handle** any exception thrown by Moshi's methods so, it's up to you handle them.

### Ignoring Exceptions

If you want to deserialize a JSON and don't want to handle the exceptions yourself, you can use the ```tryParse``` method.
```kotlin
data class MyClass(val name: String, val age: Int)

val moshi = Moshi.Builder().build()
val myJson = "[{\"name\":\"Darth Vader\",\"age\":45},{\"name\":\"Luke Skywalker\",\"age\":21}]"

val myNullableInstance = moshi.tryParse<List<MyClass>>(myJson)
```
 
The ```tryParse``` handle many exceptions that can be thrown by Moshi's methods, and, in that case, **it will return a null value** so, in this case, you will have to check for null.

This method also works with ```String```, ```BufferedSource``` and ```JsonReader``` parameters.
