package com.example.kapellmeister.Holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.databinding.VModelSongUnitBinding

class SoundHolder(binding: VModelSongUnitBinding): RecyclerView.ViewHolder(binding.root) {
    val title = binding.modelSongName
    val author = binding.modelSongAuthorName
    val img = binding.modelSongImg
    val time = binding.modelSongTime
}