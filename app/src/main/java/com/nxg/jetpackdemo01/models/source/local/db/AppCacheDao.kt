package com.nxg.jetpackdemo01.models.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nxg.jetpackdemo01.models.entity.AppCache

/**
 * ================================================
 * Created by xiangang on 2020/2/7 23:29
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */

@Dao
interface AppCacheDao {

    @Query("SELECT * FROM cache ORDER BY id")
    fun getAllAppCache(): LiveData<List<AppCache>>

    @Query("SELECT * FROM cache WHERE id = :cacheId")
    fun getAppCache(cacheId: Int): LiveData<AppCache>

    @Query("SELECT * FROM cache WHERE name = :name")
    fun getAppCacheByName(name: String): LiveData<AppCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(caches: List<AppCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: AppCache)

    @Delete
    fun delete(cache: AppCache)
}