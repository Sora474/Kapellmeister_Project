package com.example.kapellmeister.Pages


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.FragmentListPageBinding

private lateinit var BindingClass : FragmentListPageBinding
class ListPage : Fragment(R.layout.fragment_list_page) {
    private lateinit var soundAdapter: SoundAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentListPageBinding.inflate(inflater)

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        soundAdapter = SoundAdapter(BindingClass.root.context,MainActivity.initial_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + DataSound().getSizeSoundList(MainActivity.initial_list).toString()

        return BindingClass.root
    }
}