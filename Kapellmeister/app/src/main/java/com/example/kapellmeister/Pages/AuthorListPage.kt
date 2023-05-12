package com.example.kapellmeister.Pages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.AuthorAdapter
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.Services.SoundService
import com.example.kapellmeister.databinding.FragmentAuthorListPageBinding
import com.example.kapellmeister.databinding.FragmentAuthorPageBinding
import com.example.kapellmeister.databinding.FragmentListPageBinding

private lateinit var BindingClass : FragmentAuthorListPageBinding

class AuthorListPage : Fragment(R.layout.fragment_author_list_page) {
    private lateinit var soundAdapter: SoundAdapter
    var author_sound_list = ArrayList<SoundModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentAuthorListPageBinding.inflate(inflater)

       // MainActivity.sound_list.filter { it.author == intent.getIntExtra("author_name","0")}  //  Получение ПлейЛиста
       // author_sound_list.sort() //  Преобразвание ПлейЛиста

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        soundAdapter = SoundAdapter(BindingClass.root.context, author_sound_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSoundAuthor.text =  getString(R.string.total_author_sound) + author_sound_list.size.toString()

        return BindingClass.root
    }
}