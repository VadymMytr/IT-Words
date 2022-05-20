package ua.vadymmy.it.words.di

import dagger.Component
import javax.inject.Singleton
import ua.vadymmy.it.words.MainActivity
import ua.vadymmy.it.words.di.modules.ApiModule

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {

    fun injectActivity(activity: MainActivity)
}