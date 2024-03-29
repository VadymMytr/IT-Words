package ua.vadymmy.it.words.di.modules

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import ua.vadymmy.it.words.MyApp

@Module
class ContextModule {
    @Provides
    fun provideApplicationContext(): Context = MyApp.appContext

    @Provides
    fun provideTextToSpeech(): TextToSpeech = MyApp.textToSpeech
}