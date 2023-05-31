package com.example.kapellmeister.Holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.databinding.VModelFavoriteUnitBinding
import com.example.kapellmeister.databinding.VModelSongUnitBinding

class FavoriteHolder(binding: VModelFavoriteUnitBinding): RecyclerView.ViewHolder(binding.root) {
    val name = binding.modelSoundName
    val img = binding.modelSoundImg

    val root = binding.root
}