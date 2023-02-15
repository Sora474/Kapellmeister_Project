package com.example.kapellmeister

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.databinding.ActivityMainBinding
import com.example.kapellmeister.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivityPlayerBinding

    companion object{
        lateinit var sound_list: ArrayList<SoundModel>
        var sound_position: Int = 0
        var mediaPlayer: MediaPlayer? = null
        var isPlaing: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        BindingClass.btnPlayPause.setOnClickListener(){
            if(isPlaing) pauseSound()
            else playSound()
        }

    }

    private fun initializeLayout() /* Подгрузка данных в Activity */ {
        BindingClass = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)

        sound_position = intent.getIntExtra("sound_index",0)
        when(intent.getStringExtra("sound_class")){
            "SoundAdapter" -> {
                sound_list = ArrayList()
                sound_list.addAll(ListPage.sound_list)
                setLayout()
                createMediaPlayer()
            }
        }
    }
    private fun setLayout() /* Присвоение данных */ {
        Glide.with(this)
            .load(sound_list[sound_position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef).centerCrop())
            .into(BindingClass.ivSongImg)

        BindingClass.tvSongName.text = sound_list[sound_position].name
        BindingClass.tvSongAuthor.text = sound_list[sound_position].author
    }
    private fun createMediaPlayer() /* Создание и активация плейера */ {
        try {
            if(mediaPlayer == null) mediaPlayer = MediaPlayer()
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(sound_list[sound_position].path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            playSound()
        }catch (e: Exception){return}
    }

    private fun playSound() /* Запуск плейера */ {
        BindingClass.btnPlayPause.setIconResource(R.drawable.ic_pause)
        isPlaing = true
        mediaPlayer?.start()
    }

    private fun pauseSound() /* Остановка плейера */ {
        BindingClass.btnPlayPause.setIconResource(R.drawable.ic_play)
        isPlaing = false
        mediaPlayer?.pause()
    }
}