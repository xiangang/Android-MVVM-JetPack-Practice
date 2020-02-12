package com.nxg.jetpackdemo01.di

import com.nxg.jetpackdemo01.models.repository.HomeRepository
import com.nxg.jetpackdemo01.models.repository.NCov2019Repository
import com.nxg.jetpackdemo01.models.source.local.db.AppDatabase
import com.nxg.jetpackdemo01.models.source.remote.api.UrlConstant
import com.nxg.jetpackdemo01.models.source.remote.api.WanService
import com.nxg.jetpackdemo01.models.source.remote.net.RetrofitClient
import com.nxg.jetpackdemo01.viewmodels.HomeViewModel
import com.nxg.jetpackdemo01.viewmodels.COVID19ViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ================================================
 * Created by xiangang on 2020/2/5 0:27
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { COVID19ViewModel(get()) }
}

val repositoryModule = module {
    single { RetrofitClient.getService(WanService::class.java, UrlConstant.BASE_URL) }
    single { HomeRepository(AppDatabase.getInstance(androidContext()).appCacheDao())}
    single { NCov2019Repository(AppDatabase.getInstance(androidContext()).appCacheDao())}
}

val appModule = listOf(viewModelModule, repositoryModule)