package com.nxg.jetpackdemo01.models.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.reflect.TypeToken
import com.nxg.jetpackdemo01.models.entity.AppCache
import com.nxg.jetpackdemo01.models.entity.TimelineBean
import com.nxg.jetpackdemo01.models.source.local.db.AppCacheDao
import com.nxg.jetpackdemo01.models.source.remote.net.NCov2019RetrofitClient
import com.nxg.jetpackdemo01.utils.NCOV_2019_TIME_LINE
import com.nxg.jetpackdemo01.vo.Resource

/**
 * ================================================
 * Created by xiangang on 2020/2/5 14:02
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class NCov2019Repository (private val appCacheDao: AppCacheDao)  : BaseRepository() {

    /**
     * 按时间线获取事件
     */
    fun getTimelineService():  LiveData<Resource<List<TimelineBean>>> {
        return networkBoundResource(
            saveCallResult = ::saveCallResultFromTimeLine,
            shouldFetch = { true },
            fetch = { NCov2019RetrofitClient.service.getTimelineService() },
            loadFromDb = { loadTimeLineFromDb(NCOV_2019_TIME_LINE) }
        )
    }
    private fun loadTimeLineFromDb(name: String):  LiveData<List<TimelineBean>> {
        val appCache: LiveData<AppCache> = appCacheDao.getAppCacheByName(name)
        return Transformations.switchMap(appCache){
            val temp = MutableLiveData<List<TimelineBean>>()
            val listType = object : TypeToken<List<TimelineBean>>() {}.type
            temp.postValue(getGsonIntance().fromJson(appCache.value?.data,listType))
            return@switchMap temp
        }

    }

    private suspend fun saveCallResultFromTimeLine(beanList: List<TimelineBean>){
        val time = System.currentTimeMillis().toString()
        val appCache = AppCache(-1, NCOV_2019_TIME_LINE,getGsonIntance().toJson(beanList),time,time)
        appCacheDao.insert(appCache)
    }

}