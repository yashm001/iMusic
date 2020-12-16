package com.example.imusic.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSong(song:Song)

    @Query("SELECT * FROM songs_table")
    fun fetchSongs() : LiveData<List<Song>>

}