package com.example.dynamictestapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.Request

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}

// ✅ Yeni fonksiyon: sunucuya bağlanabiliyor musun?
suspend fun isServerReachable(): Boolean {
    return try {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000/ping")
            .build()

        val response = OkHttpClient()
            .newCall(request)
            .execute()
        response.isSuccessful
    } catch (e: Exception) {
        false
    }
}