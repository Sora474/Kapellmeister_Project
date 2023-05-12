package com.example.kapellmeister.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kapellmeister.Holders.AuthorHolder
import com.example.kapellmeister.Pages.AuthorListPage
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.databinding.FragmentAuthorListPageBinding
import com.example.kapellmeister.databinding.VModelAuthorUnitBinding

class AuthorAdapter(private val context: Context, private val author_list: List<String>): RecyclerView.Adapter<AuthorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorHolder {
        return AuthorHolder(VModelAuthorUnitBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: AuthorHolder, position: Int) {
        //  Присвоение параметров author_list к пользовательскому интерфейсу
        holder.author.text = author_list[position]

        //  Передача context
        holder.root.setOnClickListener{
            val bundle = Bundle()
           // val intent = Intent(context, AuthorListPage::class.java)
           // bundle.putString("author_name",author_list[position])
           // findNav
          //  intent.putExtra("author_name", author_list[position])
           // ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return author_list.size
    }
}