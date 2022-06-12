package com.example.tutu_intern_test_cherniak.presentation.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.registerNetworkCallback(onAvailable: () -> Unit, onLost: () -> Unit) {
    val connectivityManager =
        requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.registerDefaultNetworkCallback(object :
        ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            onAvailable.invoke()
        }

        override fun onLost(network: Network) {
            onLost.invoke()
        }
    })
}

fun Fragment.checkNetworkConnectAll(): Boolean {
    var result = false
    val connectivityManager =
        requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return result
}

fun Fragment.setNavigateUpButtonToToolbar(toolbar: Toolbar) {
    with(toolbar) {
        setNavigationIcon(com.example.tutu_intern_test_cherniak.R.drawable.ic_arrow_back_24)
        setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}