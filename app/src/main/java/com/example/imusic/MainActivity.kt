package com.example.imusic

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imusic.adapters.LocalSongAdapter
import com.example.imusic.adapters.SongAdapter
import com.example.imusic.model.Song
import com.example.imusic.repository.Repository
import com.example.imusic.viewmodels.MainViewModel
import com.example.imusic.viewmodels.MainViewModelFactory
import com.example.imusic.viewmodels.SongViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mSongViewModel: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<EditText>(R.id.SearchBar)
        val searchBtn = findViewById<ImageButton>(R.id.SearchBtn)
        val noResultImage = findViewById<ImageView>(R.id.ErrorimageView)
        val noResultTextView = findViewById<TextView>(R.id.ErrortextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager

        val songs = ArrayList<Song>()
        val adapter = SongAdapter(songs)

        val localSongs = ArrayList<com.example.imusic.data.Song>()
        val localSongAdapter =
            LocalSongAdapter(localSongs)

        mSongViewModel = ViewModelProvider(this).get(SongViewModel::class.java)

        val repository = Repository()
        val viewModelFactory =
            MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        searchBtn.setOnClickListener(View.OnClickListener {
            recyclerView.adapter = adapter
            progressBar.visibility = View.VISIBLE

            val search: String = searchBar.text.toString()
            if (isNetworkConnected()) {

                viewModel.getSongs(search)

                viewModel.myResponse.observe(this, Observer { response ->
                    progressBar.visibility = View.GONE
                    songs.clear()

                        if(response.resultCount!=0 ) {

                            noResultImage.visibility = View.GONE
                            noResultTextView.visibility = View.GONE

                            for (i in 1..response.resultCount) {
                                val song: Song = response.results.get(i - 1)
                                songs.add(song)

                            }

                            adapter.notifyDataSetChanged()
                        }else{
                            adapter.notifyDataSetChanged()
                            noResultImage.visibility = View.VISIBLE
                            noResultTextView.visibility = View.VISIBLE
                        }

                    for(song:Song in songs){
                        val localSong: com.example.imusic.data.Song =
                            com.example.imusic.data.Song(
                                0,
                                search,
                                song.trackId,
                                song.trackName,
                                song.trackName,
                                song.artistName,
                                song.artworkUrl100
                            )

                        mSongViewModel.addSong(localSong)
                    }


                })

            }
            else {
                recyclerView.adapter = localSongAdapter
                Log.d("Response", "NO INTERNET CONNECTION")
                localSongs.clear()

                mSongViewModel.fetchSongs.observe(this, Observer { response ->

                    for (song: com.example.imusic.data.Song in response) {

                        if (song.keyword.toLowerCase() == search.toLowerCase() && song.artistName?.toLowerCase()
                                ?.contains(song.keyword)!! ) {
                            localSongs.add(song)
                        }

                    }
                    if(localSongs.size !=0){
                        noResultImage.visibility = View.GONE
                        noResultTextView.visibility = View.GONE
                    }
                    else{
                        noResultImage.visibility = View.VISIBLE
                        noResultTextView.visibility = View.VISIBLE
                    }
                    progressBar.visibility = View.GONE
                    localSongAdapter.notifyDataSetChanged()
                })
            }

        })

    }


    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onResume() {
        super.onResume()

        if(!isNetworkConnected()) {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setNegativeButton("Connect to Internet") {_, _ ->
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent,null)
                }
                .setPositiveButton("Search Offline") { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

}

