package com.example.kapellmeister.Holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.databinding.VModelCollectionListUnitBinding
import com.example.kapellmeister.databinding.VModelCollectionUnitBinding

class CollectionHolder(binding: VModelCollectionUnitBinding): RecyclerView.ViewHolder(binding.root) {
    val name = binding.modelCollectionName
    val img = binding.modelCollectionImg

    val root = binding.root
}