package com.nxg.jetpackdemo01.utils

import android.util.Log

/**
 * ================================================
 * Created by xiangang on 2020/2/4 23:30
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
object Timer {

    private val tagMap = hashMapOf<String, Long>()

    fun start(tag: String) {
        tagMap[tag] = System.currentTimeMillis()
    }

    fun stop(tag: String) {
        if (!tagMap.containsKey(tag)) return
        val cost = System.currentTimeMillis() - (tagMap[tag] ?: 0)
        tagMap.remove(tag)
        Log.e("timer", "$tag cost : $cost")
    }
}