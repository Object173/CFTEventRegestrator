package com.object173.cfteventregestrator.feature.base.device

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException

fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

class NoConnectivityException : IOException() {
}