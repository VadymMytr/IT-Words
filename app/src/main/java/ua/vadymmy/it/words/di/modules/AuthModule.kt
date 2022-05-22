package ua.vadymmy.it.words.di.modules

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import ua.vadymmy.it.words.R

@Module
class AuthModule {

    @Provides
    fun provideSignInOptions(applicationContext: Context) : GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(applicationContext.getString(R.string.oauth_client_id))
            .requestProfile()
            .requestEmail()
            .build()
}