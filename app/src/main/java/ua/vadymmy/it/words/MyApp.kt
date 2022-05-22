package ua.vadymmy.it.words

import android.app.Application
import android.content.Context
import androidx.room.Room
import ua.vadymmy.it.words.data.local.ROOM_DATABASE_NAME
import ua.vadymmy.it.words.data.local.RoomDB
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.di.DaggerAppComponent

class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        val injector: AppComponent get() = instance.appComponent
        val appContext: Context get() = instance.applicationContext
        val database: RoomDB get() = instance.roomDB
    }

    private lateinit var appComponent: AppComponent
    private lateinit var roomDB: RoomDB

    override fun onCreate() {
        super.onCreate()
        instance = this
        roomDB = Room.databaseBuilder(this, RoomDB::class.java, ROOM_DATABASE_NAME).fallbackToDestructiveMigration().build()
        appComponent = DaggerAppComponent.create()
    }
}