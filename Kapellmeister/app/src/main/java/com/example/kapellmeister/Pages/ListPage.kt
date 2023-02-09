package com.example.kapellmeister.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.FragmentListPageBinding

class ListPage : Fragment(R.layout.fragment_list_page) {
    lateinit var BindingClass : FragmentListPageBinding
    private lateinit var soundAdapter: SoundAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentListPageBinding.inflate(inflater)
        val sound_list = ArrayList<String>()

        sound_list.add("1 Sound")
        sound_list.add("2 Sound")
        sound_list.add("3 Sound")
        sound_list.add("4 Sound")

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        soundAdapter = SoundAdapter(BindingClass.root.context, sound_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSoundCount.text =  soundAdapter.itemCount.toString()

        return BindingClass.root
    }
}