package com.nxg.jetpackdemo01.models.source.remote.api

import com.nxg.jetpackdemo01.models.entity.AuthorInfoBean
import com.nxg.jetpackdemo01.models.entity.CategoryBean
import com.nxg.jetpackdemo01.models.entity.HomeBean
import com.nxg.jetpackdemo01.models.entity.TabInfoBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * ================================================
 * Created by xiangang on 2020/2/3 18:47
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
interface ApiService{

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    suspend fun getFirstHomeData(@Query("num") num:Int): ApiResponse<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    suspend fun getMoreHomeData(@Url url: String):ApiResponse<HomeBean>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    suspend fun getRelatedData(@Query("id") id: Long): HomeBean.Issue

    /**
     * 获取分类
     */
    @GET("v4/categories")
    suspend fun getCategory(): ArrayList<CategoryBean>

    /**
     * 获取分类详情List
     */
    @GET("v4/categories/videoList?")
    suspend fun getCategoryDetailList(@Query("id") id: Long): HomeBean.Issue

    /**
     * 获取更多的 Issue
     */
    @GET
    suspend fun getIssueData(@Url url: String): HomeBean.Issue

    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    suspend fun getRankList(): TabInfoBean

    /**
     * 获取搜索信息
     */
    @GET("v1/search?&num=10&start=10")
    suspend fun getSearchData(@Query("query") query :String) : HomeBean.Issue

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    suspend fun getHotWord(): ArrayList<String>

    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    suspend fun getFollowInfo(): HomeBean.Issue

    /**
     * 作者信息
     */
    @GET("v4/pgcs/detail/tab?")
    suspend fun getAuthorInfo(@Query("id") id: Long): AuthorInfoBean



}