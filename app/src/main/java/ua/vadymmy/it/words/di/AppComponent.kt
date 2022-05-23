package ua.vadymmy.it.words.di

import dagger.Component
import javax.inject.Singleton
import ua.vadymmy.it.words.di.modules.ApiModule
import ua.vadymmy.it.words.di.modules.AuthModule
import ua.vadymmy.it.words.di.modules.ContextModule
import ua.vadymmy.it.words.di.modules.DataModule
import ua.vadymmy.it.words.ui.activities.AuthActivity
import ua.vadymmy.it.words.ui.activities.MainActivity
import ua.vadymmy.it.words.ui.activities.SyncActivity

@Singleton
@Component(modules = [ApiModule::class, AuthModule::class, DataModule::class, ContextModule::class])
interface AppComponent {

    fun injectActivity(activity: MainActivity)
    fun injectActivity(activity: AuthActivity)
    fun injectActivity(activity: SyncActivity)
}