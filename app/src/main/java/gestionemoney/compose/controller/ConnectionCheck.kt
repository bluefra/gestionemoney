package gestionemoney.compose.controller

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * utility class utilized to check the internet access of the device
 */
class ConnectionCheck {
    /**
     * @return true if the device is properly connected to the network, false otherwise
     */
    fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}