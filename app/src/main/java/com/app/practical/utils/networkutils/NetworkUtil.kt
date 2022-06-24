package com.app.practical.utils.networkutils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


object NetworkUtil {

    private val NETWORK_STATUS_NOT_CONNECTED = 0
    private val NETWORK_STAUS_WIFI = 1
    private val NETWORK_STATUS_MOBILE = 2
    private val TYPE_WIFI = 1
    private val TYPE_MOBILE = 2
    private val TYPE_NOT_CONNECTED = 0

    private fun getConnectivityStatus(context: Context): Int {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI

            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE
            if (activeNetwork.isConnected) {
                Log.e("NetworkUtil", "connected")
            } else {
                Log.e("NetworkUtil", "not connected")
            }
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context): String? {
        val conn = getConnectivityStatus(context)
        var status: String? = null
        when (conn) {
            TYPE_WIFI -> status = "Wifi enabled"
            TYPE_MOBILE -> status = "Mobile data enabled"
            TYPE_NOT_CONNECTED -> status = "Not connected to Internet"

        }
        return status
    }

    private fun getConnectivityStatusInt(context: Context): Int {
        val conn = getConnectivityStatus(context)
        var status = 0
        if (conn == TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED
        }
        return status
    }

    fun isNetworkConnected(context: Context): Boolean {
        return getConnectivityStatusInt(context) != NETWORK_STATUS_NOT_CONNECTED
    }

    fun isConnected(context: Context): Boolean {
        var connected = false
        try {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message!!)
        }
        return connected
    }

    fun isNetworkOnline(): Boolean {
        var isOnline = false
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53), 3000)
            isOnline = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return isOnline
    }

}
