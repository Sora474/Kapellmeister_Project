package com.example.kapellmeister.Pages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.FragmentListPageBinding
import com.example.kapellmeister.databinding.FragmentNowPlayingBinding

private lateinit var BindingClass : FragmentNowPlayingBinding

class NowPlaying : Fragment(R.layout.fragment_now_playing) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentNowPlayingBinding.inflate(inflater)

        BindingClass.btnPlayPause.setOnClickListener(){
            if(MainActivity.isPlaing) DataSound().pauseSound()
            else DataSound().playSound()
        }
        BindingClass.btnNext.setOnClickListener(){
            DataSound().moveSound(true, BindingClass.root.context)
        }
        BindingClass.root.setOnClickListener(){
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("sound_index", MainActivity.sound_position)
            intent.putExtra("sound_class", "NowPlaying")
            ContextCompat.startActivity(requireContext(), intent, null)
        }

        return BindingClass.root
    }

    override fun onResume() {
        super.onResume()
        setLayout(BindingClass.root.context)
    }

    fun setLayout(context: Context) /* Присвоение визуальных данных */ {
        if(MainActivity.soundService != null){
            BindingClass.root.visibility = View.VISIBLE

            BindingClass.tvSoundName.isSelected = true
            Glide.with(context)
                .load(MainActivity.sound_list[MainActivity.sound_position].artUri)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_white).centerCrop())
                .into(BindingClass.ivSoundImg)

            BindingClass.tvSoundName.text = MainActivity.sound_list[MainActivity.sound_position].name
            initializeBtnPlayPause()
        }else{
            BindingClass.root.visibility = View.GONE
        }
    }
    fun initializeBtnPlayPause() /* Инициализация отображения кнопки Запуска/Останови аудио файла плейера */ {
        if(MainActivity.isPlaing) BindingClass.btnPlayPause.setImageResource(
            R.drawable.ic_pause
        )
        else BindingClass.btnPlayPause.setImageResource(R.drawable.ic_play)
    }
}