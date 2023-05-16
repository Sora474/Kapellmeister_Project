package com.example.kapellmeister

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.text.style.TabStopSpan
import android.view.Menu
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity.Companion.isPlaing
import com.example.kapellmeister.MainActivity.Companion.sound_position
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.Services.SoundService
import com.example.kapellmeister.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection{
    companion object{
        lateinit var BindingClass : ActivityPlayerBinding
        private lateinit var runnable: Runnable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        BindingClass.btnPlayPause.setOnClickListener(){
            if(isPlaing) DataSound().pauseSound()
            else DataSound().playSound()
        }
        BindingClass.btnNext.setOnClickListener(){
            DataSound().moveSound(true, this)
        }
        BindingClass.btnPrevious.setOnClickListener(){
            DataSound().moveSound(false, this)
        }
        BindingClass.btnShuffle.setOnClickListener(){
            DataSound().changeStatusSoundShuffle()
            initializeBtnShuffle()
        }
        BindingClass.btnRepeat.setOnClickListener(){
            DataSound().changeStatusSoundRepeat()
            initializeBtnRepeat()
        }
        BindingClass.btnFavorite.setOnClickListener(){
            initializeBtnFavorite()
        }
        BindingClass.btnDown.setOnClickListener(){
            finish()
        }
        BindingClass.sbSongLine.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) MainActivity.soundService?.mediaPlayer?.seekTo(p1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?)  = Unit
        })
    }

    private fun initializeLayout() /* Подгрузка данных в Activity */ {
        BindingClass = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)
        initializeBtnPlayPause()
        initializeBtnRepeat()
        initializeBtnShuffle()

        sound_position = intent.getIntExtra("sound_index",0)
        when(intent.getStringExtra("sound_class")){
            "NowPlaying" -> {
                setLayout(this)
            }
            "SoundAdapter" -> {
                setLayout(this)

                ////////////////////    For starting service
                val intent = Intent(this, SoundService()::class.java)
                bindService(intent, this, BIND_AUTO_CREATE) //  Авторежим коннекта и дисконнекта
                startService(intent)
            }
        }
    }

    fun initializeBtnPlayPause() /* Инициализация отображения кнопки Запуска/Останови аудио файла плейера */ {
        if(MainActivity.isPlaing) BindingClass.btnPlayPause.setIconResource(R.drawable.ic_pause)
        else BindingClass.btnPlayPause.setIconResource(R.drawable.ic_play)
    }
    fun initializeBtnFavorite() /* Инициализация отображения кнопки Запуска/Останови аудио файла плейера */ {
        BindingClass.btnFavorite.setImageResource(R.drawable.ic_favorite_true)
    }
    private fun initializeBtnRepeat() /* Инициализация отображения кнопки статуса активности повтора аудио файла плейера */ {
        when(MainActivity.isRepeat){
            0 -> {
                BindingClass.btnRepeat.setImageResource(R.drawable.ic_repeat_off)
                BindingClass.btnRepeat.setColorFilter(ContextCompat.getColor(this,R.color.Gray))
            }
            1 -> {
                BindingClass.btnRepeat.setImageResource(R.drawable.ic_repeat_on)
                BindingClass.btnRepeat.setColorFilter(ContextCompat.getColor(this,R.color.white))
            }
            2 -> {
                BindingClass.btnRepeat.setImageResource(R.drawable.ic_repeat_one_on)
                BindingClass.btnRepeat.setColorFilter(ContextCompat.getColor(this,R.color.white))
            }
        }
    }
    private fun initializeBtnShuffle() /* Инициализация отображения кнопки статуса активности случайного смещения аудио файла плейера */ {
        if(MainActivity.isShuffle) {
            BindingClass.btnShuffle.setImageResource(R.drawable.ic_shuffle_on)
            BindingClass.btnShuffle.setColorFilter(ContextCompat.getColor(this,R.color.white))
        }
        else {
            BindingClass.btnShuffle.setImageResource(R.drawable.ic_shuffle_off)
            BindingClass.btnShuffle.setColorFilter(ContextCompat.getColor(this,R.color.Gray))
        }
    }

    fun setLayout(context: Context) /* Присвоение визуальных данных */ {
        Glide.with(context)
            .load(MainActivity.sound_list[sound_position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_white).centerCrop())
            .into(BindingClass.ivSongImg)

        BindingClass.tvSongName.text      = MainActivity.sound_list[sound_position].name
        BindingClass.tvSongAuthor.text    = MainActivity.sound_list[sound_position].author
        BindingClass.tvSongTimeEnd.text   = DataSound().TimeFormat(MainActivity.sound_list[sound_position].time)
    }

    fun setSeekBarView(){
        runnable = Runnable {
            BindingClass.tvSongTimeBegin.text = DataSound().TimeFormat(MainActivity.soundService?.mediaPlayer!!.currentPosition.toLong())
            BindingClass.sbSongLine.apply {
                progress = MainActivity.soundService?.mediaPlayer!!.currentPosition
                max = MainActivity.sound_list[sound_position].time.toInt()
            }
            Handler(Looper.getMainLooper()).postDelayed(runnable, 400)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) /* Действия при активации SoundService */ {
        val binder = p1 as SoundService.MyBinder
        MainActivity.soundService = binder.currentService()
        DataSound().createMediaPlayer()
        DataSound().completedSound(this)
    }

    override fun onServiceDisconnected(p0: ComponentName?) /* Действия при прекращении работы SoundService */ {
        MainActivity.soundService = null
    }
}