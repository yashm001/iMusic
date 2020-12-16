package com.example.imusic.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.imusic.data.Song
import com.example.imusic.data.SongDatabase
import com.example.imusic.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongViewModel(application: Application): AndroidViewModel(application) {

    val fetchSongs :LiveData<List<Song>>
    private val repository: SongRepository

    init {
        val songDao = SongDatabase.getDatabase(application).songDao()
        repository = SongRepository(songDao)
        fetchSongs = repository.fetchSongs
    }

    fun addSong(song: Song) {
        viewModelScope.launch  (Dispatchers.IO) {
            repository.addSong(song)

        }
    }
}