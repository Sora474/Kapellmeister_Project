package com.example.kapellmeister.Datas

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.Services.SoundService
import java.util.concurrent.TimeUnit


data class SoundModel(val id:String, val name:String, val author:String,
                      val album:String, val time:Long, val path:String, val artUri:String){}
class DataSound(){
    fun TimeFormat(time: Long): String /* Формаирование времени */ {
        val minutes = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS)
        val seconds = TimeUnit.SECONDS.convert(time, TimeUnit.MILLISECONDS) - (60 * TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS))
        return String.format("%02d:%02d",minutes, seconds)
    }
    fun getImageArt(path: String): ByteArray? /* Получение изображения аудио файла плейера */ {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        return retriever.embeddedPicture
    }
    fun getSizeSoundList(sound_list: ArrayList<SoundModel>): Int /* Получение размера плэйлиста */ {
        return sound_list.size
    }
    fun createMediaPlayer() /* Создание и активация плейера */ {
        try {
                if(MainActivity.soundService?.mediaPlayer == null) MainActivity.soundService?.mediaPlayer = MediaPlayer()
            MainActivity.soundService?.mediaPlayer?.reset()
            MainActivity.soundService?.mediaPlayer?.setDataSource(MainActivity.sound_list[MainActivity.sound_position].path)
            MainActivity.soundService?.mediaPlayer?.prepare()
            playSound()
        }catch (e: Exception){return}
    }
    fun playSound() /* Запуск плейера */ {
        MainActivity.isPlaing = true
        MainActivity.soundService?.mediaPlayer?.start()
        changePlauPauseSound()
    }
    fun pauseSound() /* Остановка плейера */ {
        MainActivity.isPlaing = false
        MainActivity.soundService?.mediaPlayer?.pause()
        changePlauPauseSound()
    }
    private fun changePlauPauseSound() /* Связанные действия Запуска/Останови плейера */ {
       /* if(MainActivity.isPlaing){
            PlayerActivity().BindingClass.btnPlayPause.setIconResource(R.drawable.ic_play)
        }
        else{
            PlayerActivity().BindingClass.btnPlayPause.setIconResource(R.drawable.ic_pause)
        }*/
        MainActivity.soundService?.showNotification()

    }
    fun moveSound(crement: Boolean) /* Смещение аудио файла плейера */ {
        if(crement)
            setCanMoveSound(true)
        else
            setCanMoveSound(false)

        createMediaPlayer()
        //PlayerActivity().setLayout()
    }
    fun setCanMoveSound(crement: Boolean) /* Определение возможности смещение аудио файла плейера */ {
        if(crement){
            if (MainActivity.sound_position < MainActivity.sound_list.size - 1)
                MainActivity.sound_position++
            else MainActivity.sound_position = 0
        }
        else{
            if (MainActivity.sound_position == 0)
                MainActivity.sound_position = MainActivity.sound_list.size - 1
            else MainActivity.sound_position--
        }
    }
    fun changeStatusSoundShuffle() /* Изменение статуса активности случайного смещение аудио файла плейера */ {
        MainActivity.isShuffle = MainActivity.isShuffle.not()
    }
    fun moveSoundShuffle() /* Случайное смещение аудио файла плейера */ {
        // дописать
        if(MainActivity.isShuffle){
            MainActivity.sound_position = (0 until MainActivity.sound_list.size).random()
            createMediaPlayer()

        }else {

        }
    }
}



