package com.example.kapellmeister.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.FavoriteAdapter
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.FragmentFavoritePageBinding
import com.example.kapellmeister.databinding.FragmentListPageBinding
import com.google.gson.GsonBuilder

private lateinit var BindingClass : FragmentFavoritePageBinding
class FavoritePage : Fragment() {
    private lateinit var soundAdapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentFavoritePageBinding.inflate(inflater)

        getFavoriteArray()

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(10)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = GridLayoutManager(BindingClass.root.context,3)
        soundAdapter = FavoriteAdapter(BindingClass.root.context, MainActivity.favorite_sound_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + DataSound().getSizeSoundList(MainActivity.favorite_sound_list).toString()

        return BindingClass.root
    }

    override fun onResume() {
        super.onResume()
        getFavoriteArray()
        BindingClass.rvList.adapter = soundAdapter
        BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + DataSound().getSizeSoundList(MainActivity.favorite_sound_list).toString()
    }

    private fun getFavoriteArray() /* Получение ПлейЛиста */ {
        var temp_list: ArrayList<String> = DataSound().readSoundFavorite(BindingClass.root.context)
        MainActivity.favorite_sound_list.clear()
        temp_list.forEach(){MainActivity.favorite_sound_list.add(MainActivity.initial_list[it.toInt()])}
    }
}