package ua.vadymmy.it.words

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.room.Room
import java.util.Locale
import ua.vadymmy.it.words.data.local.ROOM_DATABASE_NAME
import ua.vadymmy.it.words.data.local.RoomDB
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.di.DaggerAppComponent


class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        private const val TTS_GB_VOICE = "en-gb-x-fis-local"
        val injector: AppComponent get() = instance.appComponent
        val appContext: Context get() = instance.applicationContext
        val database: RoomDB get() = instance.roomDB
        val textToSpeech: TextToSpeech get() = instance.textToSpeech
    }

    private lateinit var appComponent: AppComponent
    private lateinit var roomDB: RoomDB
    private val textToSpeech: TextToSpeech by lazy {
        TextToSpeech(this) {
            onTextToSpeechInitialized()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        roomDB = Room.databaseBuilder(this, RoomDB::class.java, ROOM_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        appComponent = DaggerAppComponent.create()
        textToSpeech.language = Locale.ENGLISH
    }

    private fun onTextToSpeechInitialized() {
        textToSpeech.voices.find { it.name == TTS_GB_VOICE }?.let {
            textToSpeech.voice = it
        }
    }
}