package com.nxg.jetpackdemo01.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ================================================
 * Created by xiangang on 2020/2/7 23:16
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
@Entity(tableName = "cache")
data class AppCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val cacheId: Int,
    val name: String,
    val data: String?,
    val createTime: String?,
    val updateTime: String?
) {
    override fun toString() = name
}
