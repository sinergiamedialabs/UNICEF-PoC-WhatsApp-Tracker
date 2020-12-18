package com.synergia.datapoc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageLog::class,SendLog::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun msgDao(): MsgDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "message.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}