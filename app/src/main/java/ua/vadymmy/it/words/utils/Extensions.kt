package ua.vadymmy.it.words.utils

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import java.util.UUID
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


val newUUID get() = UUID.randomUUID().toString()
val gson get() = Gson()

//live data ext
fun <T> MutableLiveData<T>.call(newValue: T, default: T) {
    this.value = newValue
    this.value = default
}

//coroutine ext
fun Continuation<Unit>.resume() = this.resume(Unit)

//firebase ext
private const val STRING_NOT_FOUND = ""
private const val INT_NOT_FOUND = 0
private const val BOOL_NOT_FOUND = false
fun DocumentSnapshot.findString(fieldName: String) = getString(fieldName) ?: STRING_NOT_FOUND
fun DocumentSnapshot.findInt(fieldName: String) = getLong(fieldName)?.toInt() ?: INT_NOT_FOUND
fun DocumentSnapshot.findBoolean(fieldName: String) = getBoolean(fieldName) ?: BOOL_NOT_FOUND

fun List<String>.serialize(): String = gson.toJson(this.toTypedArray())
fun DocumentSnapshot.findStringList(fieldName: String): List<String> {
    val json = findString(fieldName)
    val array = try {
        gson.fromJson(json, Array<String>::class.java)
    } catch (ex: Exception) {
        ex.printStackTrace()
        arrayOf()
    }

    return array.toList()
}

