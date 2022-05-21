package ua.vadymmy.it.words.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ua.vadymmy.it.words.data.local.RoomDataRepository
import ua.vadymmy.it.words.data.server.FirebaseServerRepository
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideLocalDataRepository(
        roomDataRepository: RoomDataRepository
    ): LocalRepository = roomDataRepository

    @Provides
    @Singleton
    fun provideServerDataRepository(
        firebaseServerRepository: FirebaseServerRepository
    ): ServerRepository = firebaseServerRepository
}