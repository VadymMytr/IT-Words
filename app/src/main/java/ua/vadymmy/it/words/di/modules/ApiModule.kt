package ua.vadymmy.it.words.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.vadymmy.it.words.data.api.images.ImageApi
import ua.vadymmy.it.words.data.api.images.ImageRepository
import ua.vadymmy.it.words.domain.api.repositories.ImageApiRepository

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
    }

    @Provides
    @Singleton
    fun provideImageApi(): ImageApi = RetrofitBuilder(ImageApi::class.java, IMAGE_API_URL).build()

    @Provides
    @Singleton
    fun provideImageRepository(imageRepository: ImageRepository): ImageApiRepository {
        return imageRepository
    }
}