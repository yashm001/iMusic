package com.example.imusic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDatabase: RoomDatabase() {

    abstract fun songDao() : SongDao

    companion object {
        @Volatile
        private var INSTANCE: SongDatabase?= null

        fun getDatabase(context: Context): SongDatabase {
            val tempInstance = INSTANCE
            if(tempInstance!= null) {
                return tempInstance
            }
            kotlin.synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}