package ua.vadymmy.it.words.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import java.util.UUID
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


val newUUID get() = UUID.randomUUID().toString()
val gson get() = Gson()

//live data ext
fun <T> MutableLiveData<T>.call(newValue: T) {
    val oldValue = this.value
    postValue(newValue)
    postValue(oldValue)
}

fun MutableLiveData<Boolean>.emit() {
    value = true
    value = false
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

//context ext
fun Context.showToast(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, LENGTH_SHORT).show()
}

fun <T> Activity.startActivity(
    activityClass: Class<T>,
    isFinishRequired: Boolean = false,
    intentData: Bundle = bundleOf()
) {
    startActivity(Intent(this, activityClass).putExtras(intentData))
    if (isFinishRequired) finish()
}