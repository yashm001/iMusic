package com.example.imusic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imusic.R
import com.example.imusic.data.Song

class LocalSongAdapter(private val songs: ArrayList<Song>) : RecyclerView.Adapter<SongAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackName: TextView
        val collectionName : TextView
        val artistName : TextView
        val songImageView : ImageView

        init {
            // Define click listener for the ViewHolder's View.
            trackName = view.findViewById(R.id.TrackName)
            collectionName = view.findViewById(R.id.CollectionNane)
            artistName = view.findViewById(R.id.ArtistName)
            songImageView = view.findViewById(R.id.SongImage)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_card, parent, false)

        return SongAdapter.ViewHolder(view)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongAdapter.ViewHolder, position: Int) {
        holder.trackName.text = songs[position].trackName
        holder.collectionName.text = songs[position].collectionName
        holder.artistName.text = songs[position].artistName
        Glide.with(holder.itemView.context)
            .load(songs[position].artworkUrl100)
            .placeholder(R.drawable.ic_search_black_24dp)
            .into(holder.songImageView)

    }


}