package com.nxg.jetpackdemo01.models.source.remote.net

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.nxg.jetpackdemo01.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * Created by xiangang on 2020/2/4 22:49
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            /*val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }*/
            if(BuildConfig.DEBUG){
                builder.addInterceptor(LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BODY)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response").build())
            }else{
                builder.addInterceptor(LoggingInterceptor.Builder()
                    .loggable(!BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response").build())
            }

            builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

            handleBuilder(builder)

            return builder.build()
        }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}