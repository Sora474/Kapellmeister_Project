package com.example.kapellmeister.Datas

import java.util.concurrent.TimeUnit

class DataSound{
}

data class SoundModel(val id:String, val name:String, val author:String,
                      val album:String, val time:Long, val path:String, val artUri:String){}

fun TimeFormat(time: Long): String /* Формаирование времени */ {
    val minutes = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS)
    val seconds = TimeUnit.SECONDS.convert(time, TimeUnit.MILLISECONDS) - (60 * TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS))
    return String.format("%02d:%02d",minutes, seconds)
}