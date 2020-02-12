package com.nxg.jetpackdemo01.models.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.nxg.jetpackdemo01.models.entity.AppCache
import com.nxg.jetpackdemo01.models.entity.HomeBean
import com.nxg.jetpackdemo01.models.source.local.db.AppCacheDao
import com.nxg.jetpackdemo01.models.source.remote.net.RetrofitClient
import com.nxg.jetpackdemo01.vo.Resource

/**
 * ================================================
 * Created by xiangang on 2020/2/5 14:02
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class HomeRepository (private val appCacheDao: AppCacheDao)  : BaseRepository() {

    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(num: Int):  LiveData<Resource<HomeBean>> {
        return networkBoundResource(
            saveCallResult = ::saveCacheFromHomeBean,
            shouldFetch = { true },
            fetch = { RetrofitClient.service.getFirstHomeData(num) },
            loadFromDb = { getHomeBeanFromCache(num,"Home") }
        )
    }

    suspend fun requestHomeData2(num: Int) = RetrofitClient.service.getFirstHomeData(num)

    private fun getHomeBeanFromCache(num: Int, name: String):  LiveData<HomeBean> {
        val appCache: LiveData<AppCache> = appCacheDao.getAppCacheByName(name)
        return Transformations.switchMap(appCache){
            val temp = MutableLiveData<HomeBean>()
            temp.postValue(getGsonIntance().fromJson(appCache.value?.data,HomeBean::class.java))
            return@switchMap temp
        }

    }

    private suspend fun saveCacheFromHomeBean(bean: HomeBean){
        val time = System.currentTimeMillis().toString()
        val appCache = AppCache(-1,"Home",getGsonIntance().toJson(bean),time,time)
        appCacheDao.insert(appCache)
    }
    /**
     * 加载更多
     */
    fun loadMoreData(num:Int):  LiveData<Resource<HomeBean>> {
        return networkBoundResource(
            saveCallResult = {},
            shouldFetch = { false },
            fetch = { RetrofitClient.service.getFirstHomeData(num) },
            loadFromDb = { MutableLiveData<HomeBean>() }
        )
    }

    fun sayHello() {
        println("Hello from HomeRepository")
    }
}