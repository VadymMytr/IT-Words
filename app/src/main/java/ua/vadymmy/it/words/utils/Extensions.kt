package ua.vadymmy.it.words.utils

import androidx.lifecycle.MutableLiveData
import java.util.UUID

val newUUID get() = UUID.randomUUID().toString()

//live data ext
fun <T> MutableLiveData<T>.call(newValue: T, default: T) {
    this.value = newValue
    this.value = default
}