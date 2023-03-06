package com.example.kapellmeister

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.style.TabStopSpan
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity.Companion.isPlaing
import com.example.kapellmeister.MainActivity.Companion.sound_position
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.Services.SoundService
import com.example.kapellmeister.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection {
    lateinit var BindingClass : ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        BindingClass.btnPlayPause.setOnClickListener(){
            if(isPlaing){
                DataSound().pauseSound()
                BindingClass.btnPlayPause.setIconResource(R.drawable.ic_play)
            }
            else {
                DataSound().playSound()
                BindingClass.btnPlayPause.setIconResource(R.drawable.ic_pause)
            }
        }
        BindingClass.btnNext.setOnClickListener(){
            DataSound().moveSound(true)
            setLayout()
        }
        BindingClass.btnPrevious.setOnClickListener(){
            DataSound().moveSound(false)
            setLayout()
        }
        BindingClass.btnShuffle.setOnClickListener(){
            DataSound().changeStatusSoundShuffle()

            if(MainActivity.isShuffle){ BindingClass.btnShuffle.setImageResource(R.drawable.ic_shuffle_on) }
            else{ BindingClass.btnShuffle.setImageResource(R.drawable.ic_shuffle_off) }
        }

    }

    private fun initializeLayout() /* Подгрузка данных в Activity */ {
        BindingClass = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)

        sound_position = intent.getIntExtra("sound_index",0)
        when(intent.getStringExtra("sound_class")){
            "SoundAdapter" -> {
                MainActivity.sound_list = ArrayList()
                MainActivity.sound_list.addAll(ListPage.sound_list)
                setLayout()

                ////////////////////    For starting service
                val intent = Intent(this, SoundService()::class.java)
                bindService(intent, this, BIND_AUTO_CREATE) //  Авторежим коннекта и дисконнекта
                startService(intent)
            }
        }
    }
    fun setLayout() /* Присвоение визуальных данных */ {
        Glide.with(this)
            .load(MainActivity.sound_list[sound_position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_white).centerCrop())
            .into(BindingClass.ivSongImg)

        BindingClass.tvSongName.text = MainActivity.sound_list[sound_position].name
        BindingClass.tvSongAuthor.text = MainActivity.sound_list[sound_position].author
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) /* Действия при активации SoundService */ {
        val binder = p1 as SoundService.MyBinder
        MainActivity.soundService = binder.currentService()
        DataSound().createMediaPlayer()
    }

    override fun onServiceDisconnected(p0: ComponentName?) /* Действия при прекращении работы SoundService */ {
        MainActivity.soundService = null
    }
}