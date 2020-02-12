package com.nxg.jetpackdemo01.models.source.remote.api

import com.nxg.jetpackdemo01.models.entity.TimelineBean
import retrofit2.http.GET

/**
 * ================================================
 * Created by xiangang on 2020/2/3 18:47
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
interface COVID19ApiService{

    /**
     * 按时间线获取事件
     */
    @GET("getTimelineService")
    suspend fun getTimelineService(): ApiResponse<List<TimelineBean>>


}