package ua.vadymmy.it.words.di

import dagger.Component
import javax.inject.Singleton
import ua.vadymmy.it.words.di.modules.ApiModule
import ua.vadymmy.it.words.di.modules.ContextModule
import ua.vadymmy.it.words.di.modules.DataModule
import ua.vadymmy.it.words.ui.activities.MainActivity

@Singleton
@Component(modules = [ApiModule::class, DataModule::class, ContextModule::class])
interface AppComponent {

    fun injectActivity(activity: MainActivity)
}