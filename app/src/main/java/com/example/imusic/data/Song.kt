package com.example.imusic.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imusic.model.Song

@Entity(tableName = "Songs_table")
data class Song (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val keyword : String,
    var trackId: Int?,
    var trackName: String?,
    var collectionName: String?,
    var artistName: String?,
    var artworkUrl100: String?

)