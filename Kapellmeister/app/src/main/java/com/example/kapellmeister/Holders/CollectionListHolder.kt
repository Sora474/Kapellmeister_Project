package com.example.kapellmeister.Holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.databinding.VModelCollectionListUnitBinding

class CollectionListHolder(binding: VModelCollectionListUnitBinding): RecyclerView.ViewHolder(binding.root) {
    val name = binding.modelCollectionListName
    val img = binding.modelCollectionListImg

    val root = binding.root
}