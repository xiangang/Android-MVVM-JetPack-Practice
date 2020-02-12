package com.nxg.jetpackdemo01.models.entity

/**
 * ================================================
 * Created by xiangang on 2020/2/3 18:58
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
data class BaseResp<T>(
    var code: Int = 0,
    var msg: String = "",
    var `data`: T
)