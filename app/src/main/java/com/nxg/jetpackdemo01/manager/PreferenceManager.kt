package com.nxg.jetpackdemo01.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


/**
 * ================================================
 * Created by xiangang on 2020/2/11 23:14
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class PreferenceManager private constructor(context: Context) {

    companion object {
        private var sPreferences: SharedPreferences? =null
        @Volatile private var instance: com.nxg.jetpackdemo01.manager.PreferenceManager? = null

        const val APP_VERSION_CODE = "app_version_code"
        const val APP_SKIN_INDEX = "app_skin_index"

        fun getInstance(context: Context): com.nxg.jetpackdemo01.manager.PreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: com.nxg.jetpackdemo01.manager.PreferenceManager(context).also { instance = it }
            }
        }
    }

    init {
        sPreferences =PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext())
    }

    var skinIndex: Int
        get() = sPreferences!!.getInt(APP_SKIN_INDEX, QDSkinManager.SKIN_BLUE)
        set(index) {
            val editor = sPreferences!!.edit()
            editor.putInt(APP_SKIN_INDEX, index)
            editor.apply()
        }
}