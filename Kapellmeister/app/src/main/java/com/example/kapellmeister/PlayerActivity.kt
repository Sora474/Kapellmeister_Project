package com.example.kapellmeister

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity.Companion.isPlaing
import com.example.kapellmeister.MainActivity.Companion.sound_position
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
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
                DataSound().createMediaPlayer()
            }
        }
    }
    private fun setLayout() /* Присвоение данных */ {
        Glide.with(this)
            .load(MainActivity.sound_list[sound_position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_white).centerCrop())
            .into(BindingClass.ivSongImg)

        BindingClass.tvSongName.text = MainActivity.sound_list[sound_position].name
        BindingClass.tvSongAuthor.text = MainActivity.sound_list[sound_position].author
    }
}