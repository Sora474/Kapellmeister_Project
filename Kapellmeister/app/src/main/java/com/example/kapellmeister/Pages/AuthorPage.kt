package com.example.kapellmeister.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.AuthorAdapter
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.Services.SoundService
import com.example.kapellmeister.databinding.FragmentAuthorPageBinding


private lateinit var BindingClass : FragmentAuthorPageBinding

class AuthorPage : Fragment(R.layout.fragment_author_page) {
    private lateinit var authorAdapter: AuthorAdapter
    lateinit  var author_list: List<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentAuthorPageBinding.inflate(inflater)

        var temp_list = ArrayList<String>()
        MainActivity.initial_list.forEach{temp_list.add(it.author)}  //  Получение ПлейЛиста
        author_list = temp_list.toSortedSet().toList() //  Преобразвание ПлейЛиста

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        authorAdapter = AuthorAdapter(BindingClass.root.context, author_list)
        BindingClass.rvList.adapter = authorAdapter

        BindingClass.tvTotalAuthor.text =  getString(R.string.total_author) + author_list.size.toString()

        return BindingClass.root
    }
}