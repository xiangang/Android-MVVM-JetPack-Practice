package com.nxg.jetpackdemo01.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxg.jetpackdemo01.models.repository.HomeRepository
import kotlinx.coroutines.launch

/**
 * ================================================
 * Created by xiangang on 2020/2/5 16:14
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    fun requestHomeData() = repository.requestHomeData(10)
    fun requestHomeData2() {
        viewModelScope.launch {
            val result = repository.requestHomeData2(10)
            com.orhanobut.logger.Logger.e(result.toString())
        }
    }

    fun sayHello(){
        repository.sayHello()
    }

}