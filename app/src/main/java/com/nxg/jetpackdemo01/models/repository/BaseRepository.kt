package com.nxg.jetpackdemo01.models.repository

import com.google.gson.Gson

/**
 * ================================================
 * Created by xiangang on 2020/2/5 14:04
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */

open class BaseRepository {

    companion object {

        fun getGsonIntance(): Gson =  Gson()
    }

}