package ua.vadymmy.it.words.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.vadymmy.it.words.data.api.images.ImageApi
import ua.vadymmy.it.words.data.api.images.ImageApiRepository
import ua.vadymmy.it.words.data.api.transcriptions.TranscriptionApi
import ua.vadymmy.it.words.data.api.transcriptions.TranscriptionApiRepository
import ua.vadymmy.it.words.data.local.RoomDataRepository
import ua.vadymmy.it.words.data.server.FirebaseServerRepository
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.api.images.ImageRepository
import ua.vadymmy.it.words.domain.api.transcription.TranscriptionRepository

@Module
class ApiModule {

    private class RetrofitBuilder<API_TYPE>(
        private val apiClass: Class<API_TYPE>,
        private val baseUrl: String
    ) {
        fun build(): API_TYPE =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(apiClass)
    }

    companion object {
        private const val IMAGE_API_URL = "https://bing-image-search1.p.rapidapi.com/"
        private const val TRANSCRIPTION_API_URL = "https://wordsapiv1.p.rapidapi.com/"
    }

    @Provides
    @Singleton
    fun provideImageApi(): ImageApi = RetrofitBuilder(
        ImageApi::class.java,
        IMAGE_API_URL
    ).build()

    @Provides
    @Singleton
    fun provideImageRepository(
        imageApiRepository: ImageApiRepository
    ): ImageRepository = imageApiRepository

    @Provides
    @Singleton
    fun provideTranscriptionApi(): TranscriptionApi = RetrofitBuilder(
        TranscriptionApi::class.java,
        TRANSCRIPTION_API_URL
    ).build()

    @Provides
    @Singleton
    fun provideTranscriptionRepository(
        transcriptionApiRepository: TranscriptionApiRepository
    ): TranscriptionRepository = transcriptionApiRepository

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