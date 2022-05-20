package ua.vadymmy.it.words

import android.app.Application
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.di.DaggerAppComponent

class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        val injector: AppComponent get() = instance.appComponent
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.create()
    }
}