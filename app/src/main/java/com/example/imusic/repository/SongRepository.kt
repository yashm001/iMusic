package com.example.imusic.repository

import androidx.lifecycle.LiveData
import com.example.imusic.data.Song
import com.example.imusic.data.SongDao

class SongRepository(private val songDao: SongDao) {

    val fetchSongs: LiveData<List<Song>> = songDao.fetchSongs()

    suspend fun addSong(song : Song){
        songDao.addSong(song)
    }
}