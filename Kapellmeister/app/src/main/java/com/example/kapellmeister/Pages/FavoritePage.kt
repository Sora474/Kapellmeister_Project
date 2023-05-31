package com.example.kapellmeister.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.FavoriteAdapter
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.FragmentFavoritePageBinding
import com.example.kapellmeister.databinding.FragmentListPageBinding

private lateinit var BindingClass : FragmentFavoritePageBinding
class FavoritePage : Fragment() {
    private lateinit var soundAdapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentFavoritePageBinding.inflate(inflater)

        var temp_list = ArrayList<SoundModel>()
       // MainActivity.initial_list.forEach{if (it.author == intent.getStringExtra("author_name")) temp_list.add(it)}  //  Получение ПлейЛиста
       // MainActivity.author_sound_list = temp_list //  Преобразвание ПлейЛиста

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        soundAdapter = FavoriteAdapter(BindingClass.root.context, MainActivity.initial_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + DataSound().getSizeSoundList(MainActivity.initial_list).toString()

        return BindingClass.root
    }

}