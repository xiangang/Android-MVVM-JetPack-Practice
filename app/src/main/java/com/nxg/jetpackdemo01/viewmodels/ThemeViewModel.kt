package com.nxg.jetpackdemo01.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nxg.jetpackdemo01.R
import com.nxg.jetpackdemo01.models.repository.NCov2019Repository
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_TENCENT

/**
 * ================================================
 * Created by xiangang on 2020/2/5 16:14
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class ThemeViewModel() : ViewModel() {

    private val _themeColor =  MutableLiveData<Int>().apply {
        value = R.color.colorPrimaryTencent
    }

    val themeColor: MutableLiveData<Int> = _themeColor

}