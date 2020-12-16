package com.example.imusic.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.imusic.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SongDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: SongDatabase
    private lateinit var songDao: SongDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SongDatabase::class.java
        ).allowMainThreadQueries().build()

        songDao = database.songDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addSong() = runBlockingTest {
        val song = Song(1,"keyword",1234,"first Song",
            "first collection","Singer","url")

        songDao.addSong(song)

        val allSongs = songDao.fetchSongs().getOrAwaitValue()

        assertThat(allSongs).contains(song)

    }

}