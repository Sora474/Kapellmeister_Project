package com.example.kapellmeister.Holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.databinding.VModelSongUnitBinding

class SoundHolder(binding: VModelSongUnitBinding): RecyclerView.ViewHolder(binding.root) {
    val name = binding.modelSongName
    val author = binding.modelSongAuthorName
    val img = binding.modelSongImg
    val time = binding.modelSongTime

    val root = binding.root
}