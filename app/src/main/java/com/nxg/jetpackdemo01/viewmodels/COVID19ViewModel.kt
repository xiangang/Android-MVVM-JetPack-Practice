package com.nxg.jetpackdemo01.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nxg.jetpackdemo01.models.repository.NCov2019Repository
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_TENCENT

/**
 * ================================================
 * Created by xiangang on 2020/2/5 16:14
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class COVID19ViewModel(private val repository: NCov2019Repository) : ViewModel() {

    private val _url =  MutableLiveData<String>().apply {
        value = NCOV_2019_URL_TENCENT
    }

    val nCov2019Url: MutableLiveData<String> = _url

    fun setUrl(url: String ) {
        nCov2019Url.postValue(url)
    }

    fun getTimelineService() = repository.getTimelineService()



}