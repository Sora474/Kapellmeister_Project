package com.example.kapellmeister.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.Holders.SoundHolder
import com.example.kapellmeister.databinding.VModelSongUnitBinding

class SoundAdapter(private val context: Context, private val sound_list: ArrayList<String>): RecyclerView.Adapter<SoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
        return SoundHolder(VModelSongUnitBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: SoundHolder, position: Int) {
        holder.title.text = sound_list[position]
    }

    override fun getItemCount(): Int {
        return sound_list.size
    }
}