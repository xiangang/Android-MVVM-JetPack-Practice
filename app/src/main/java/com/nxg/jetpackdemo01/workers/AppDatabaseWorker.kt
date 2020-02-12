package com.nxg.jetpackdemo01.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

/**
 * ================================================
 * Created by xiangang on 2020/2/7 23:10
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
class AppDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        /*try {
            applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val cacheType = object : TypeToken<List<AppCache>>() {}.type
                    val caches: List<AppCache> = Gson().fromJson(jsonReader, cacheType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.appCacheDao().insertAll(caches)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error app database", ex)
            Result.failure()
        }*/
        Result.success()
    }

    companion object {
        private val TAG = AppDatabaseWorker::class.java.simpleName
    }
}