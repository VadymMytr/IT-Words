package ua.vadymmy.it.words.di

import dagger.Component
import javax.inject.Singleton
import ua.vadymmy.it.words.di.modules.ApiModule
import ua.vadymmy.it.words.di.modules.AuthModule
import ua.vadymmy.it.words.di.modules.ContextModule
import ua.vadymmy.it.words.di.modules.DataModule
import ua.vadymmy.it.words.ui.activities.AuthActivity
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity
import ua.vadymmy.it.words.ui.activities.SearchActivity
import ua.vadymmy.it.words.ui.activities.SplashActivity
import ua.vadymmy.it.words.ui.activities.SyncActivity
import ua.vadymmy.it.words.ui.activities.WordsDetailsActivity
import ua.vadymmy.it.words.ui.activities.main.MainActivity
import ua.vadymmy.it.words.ui.activities.testing.LearningActivity
import ua.vadymmy.it.words.ui.fragments.LearningKitsFragment
import ua.vadymmy.it.words.ui.fragments.MainFragment
import ua.vadymmy.it.words.ui.fragments.PredefinedKitsFragment

@Singleton
@Component(modules = [ApiModule::class, AuthModule::class, DataModule::class, ContextModule::class])
interface AppComponent {

    fun injectActivity(activity: SplashActivity)
    fun injectActivity(activity: MainActivity)
    fun injectActivity(activity: AuthActivity)
    fun injectActivity(activity: SyncActivity)
    fun injectActivity(activity: KitDetailsActivity)
    fun injectActivity(activity: SearchActivity)
    fun injectActivity(activity: LearningActivity)
    fun injectActivity(activity: WordsDetailsActivity)

    fun injectFragment(fragment: MainFragment)
    fun injectFragment(fragment: LearningKitsFragment)
    fun injectFragment(fragment: PredefinedKitsFragment)
}