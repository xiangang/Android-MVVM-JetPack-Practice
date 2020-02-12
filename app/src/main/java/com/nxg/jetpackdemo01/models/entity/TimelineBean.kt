package com.nxg.jetpackdemo01.models.entity

data class TimelineBean(
    val adoptType: Int,
    val createTime: Long,
    val dataInfoOperator: String,
    val dataInfoState: Int,
    val dataInfoTime: Long,
    val entryWay: Int,
    val id: Int,
    val infoSource: String,
    val infoType: Int,
    val modifyTime: Long,
    val provinceId: String,
    val provinceName: String,
    val pubDate: Long,
    val pubDateStr: String,
    val sourceUrl: String,
    val summary: String,
    val title: String
)