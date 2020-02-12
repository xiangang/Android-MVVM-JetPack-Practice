package com.nxg.jetpackdemo01

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.NonNull
import com.nxg.jetpackdemo01.di.appModule
import com.nxg.jetpackdemo01.manager.QDSkinManager
import com.nxg.jetpackdemo01.utils.APP_START
import com.nxg.jetpackdemo01.utils.Timer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import kotlin.properties.Delegates




/**
 * ================================================
 * Created by xiangang on 2020/2/4 23:26
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        //lateinit var CURRENT_USER: User
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Timer.start(APP_START)
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Logger.addLogAdapter(AndroidLogAdapter())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

        QMUISwipeBackActivityManager.init(this)
        QDSkinManager.install(this)
        initX5WebView()

    }

    override fun onConfigurationChanged(@NonNull newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_YES) {
            QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
        } else if (QDSkinManager.currentSkin == QDSkinManager.SKIN_DARK) {
            QDSkinManager.changeSkin(QDSkinManager.SKIN_BLUE)
        }
    }

    private fun initX5WebView() {
        val map = mapOf(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
            TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true)
        QbSdk.initTbsSettings(map)

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        val cb = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                if (arg0) {
                    Logger.d( "X5 内核加载成功")
                } else {
                    Logger.d( "X5 内核加载失败")
                }
            }

            override fun onCoreInitFinished() {}
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}