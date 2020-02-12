package com.nxg.jetpackdemo01.models.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nxg.jetpackdemo01.models.entity.AppCache
import com.nxg.jetpackdemo01.utils.DATABASE_NAME

/**
 * ================================================
 * Created by xiangang on 2020/2/7 23:04
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */

@Database(entities = [AppCache::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appCacheDao(): AppCacheDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java,
                DATABASE_NAME
            )
                /*.addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<AppDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })*/
                .build()
        }
    }
}
