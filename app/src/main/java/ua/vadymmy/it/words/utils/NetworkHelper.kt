package ua.vadymmy.it.words.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NetworkHelper @Inject constructor(applicationContext: Context) {
    val isConnectionAvailable: Boolean
        get() = with(manager) {
            val internetType = setOf(TRANSPORT_CELLULAR, TRANSPORT_WIFI, TRANSPORT_ETHERNET)
            val capabilities = getNetworkCapabilities(activeNetwork) ?: return@with false

            return@with internetType.any { capabilities.hasTransport(it) }
        }

    private val manager: ConnectivityManager = applicationContext.getSystemService(
        CONNECTIVITY_SERVICE
    ) as ConnectivityManager
}