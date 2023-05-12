package com.example.kapellmeister.Holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.databinding.VModelAuthorUnitBinding

class AuthorHolder(binding: VModelAuthorUnitBinding): RecyclerView.ViewHolder(binding.root) {
    val author = binding.modelAuthorName

    val root = binding.root
}