package com.nxg.jetpackdemo01.manager

import android.content.Context
import android.content.res.Configuration
import com.nxg.jetpackdemo01.App
import com.nxg.jetpackdemo01.R
import com.qmuiteam.qmui.skin.QMUISkinManager


/**
 * ================================================
 * Created by xiangang on 2020/2/11 23:13
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
object QDSkinManager {
    const val SKIN_BLUE = 1
    const val SKIN_DARK = 2
    const val SKIN_WHITE = 3

    val currentSkin: Int
        get() = QMUISkinManager.defaultInstance(App.CONTEXT).currentSkin


    fun install(context: Context) {
        val skinManager = QMUISkinManager.defaultInstance(context)
        skinManager.addSkin(SKIN_BLUE, R.style.app_skin_blue)
        skinManager.addSkin(SKIN_DARK, R.style.app_skin_dark)
        skinManager.addSkin(SKIN_WHITE, R.style.app_skin_white)
        val isDarkMode =
            context.getResources().getConfiguration().uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_YES
        val storeSkinIndex = PreferenceManager.getInstance(context).skinIndex
        if (isDarkMode && storeSkinIndex != SKIN_DARK) {
            skinManager.changeSkin(SKIN_DARK)
        } else if (!isDarkMode && storeSkinIndex == SKIN_DARK) {
            skinManager.changeSkin(SKIN_BLUE)
        } else {
            skinManager.changeSkin(storeSkinIndex)
        }
    }

    fun changeSkin(index: Int) {
//        QMUISkinManager.defaultInstance(App.CONTEXT).changeSkin(index)
//        PreferenceManager.getInstance(App.CONTEXT).skinIndex = index
    }
}