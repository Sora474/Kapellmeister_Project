package com.example.kapellmeister.Datas

import android.media.MediaPlayer
import com.example.kapellmeister.MainActivity
import java.util.concurrent.TimeUnit


data class SoundModel(val id:String, val name:String, val author:String,
                      val album:String, val time:Long, val path:String, val artUri:String){}
class DataSound(){
    fun TimeFormat(time: Long): String /* Формаирование времени */ {
        val minutes = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS)
        val seconds = TimeUnit.SECONDS.convert(time, TimeUnit.MILLISECONDS) - (60 * TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS))
        return String.format("%02d:%02d",minutes, seconds)
    }
    fun getSizeSoundList(sound_list: ArrayList<SoundModel>): Int /* Получение размера плэйлиста */ {
        return sound_list.size
    }

    fun createMediaPlayer() /* Создание и активация плейера */ {
        try {
            if(MainActivity.mediaPlayer == null) MainActivity.mediaPlayer = MediaPlayer()
            MainActivity.mediaPlayer?.reset()
            MainActivity.mediaPlayer?.setDataSource(MainActivity.sound_list[MainActivity.sound_position].path)
            MainActivity.mediaPlayer?.prepare()
            MainActivity.mediaPlayer?.start()
            playSound()
        }catch (e: Exception){return}
    }
    fun playSound() /* Запуск плейера */ {
        MainActivity.isPlaing = true
        MainActivity.mediaPlayer?.start()
    }
    fun pauseSound() /* Остановка плейера */ {
        MainActivity.isPlaing = false
        MainActivity.mediaPlayer?.pause()
    }
    fun moveSound(crement: Boolean) /* Смещение аудио файла плейера */ {
        if(crement){
            setCanMoveSound(true)
            createMediaPlayer()
        }
        else{
            setCanMoveSound(false)
            createMediaPlayer()
        }
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
        if(MainActivity.isShuffle){
            MainActivity.sound_position = (0 until MainActivity.sound_list.size).random()
            createMediaPlayer()

        }else {

        }
    }

}



