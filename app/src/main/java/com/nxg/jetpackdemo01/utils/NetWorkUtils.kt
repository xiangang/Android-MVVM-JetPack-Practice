package com.nxg.jetpackdemo01.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * ================================================
 * Created by xiangang on 2020/2/4 23:24
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class NetWorkUtils {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }
    }

    fun Context.isNetworkStatusAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.let {
            it.activeNetworkInfo?.let {
                if (it.isConnected) return true
            }
        }
        return false
    }
}