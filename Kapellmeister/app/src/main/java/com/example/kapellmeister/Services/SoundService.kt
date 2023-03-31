package com.example.kapellmeister.Services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R

class SoundService : Service() {
    private var myBinder = MyBinder()
    private lateinit var mediaSession: MediaSessionCompat
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder {
        mediaSession = MediaSessionCompat(this,"Play now")
        return myBinder
    }

    inner class MyBinder: Binder() /* Возвращение значений при активации сервиса */ {
        fun currentService(): SoundService {
            return this@SoundService
        }
    }

    fun showNotification(){
        val playPauseIntent = Intent(this, ReceiverNotification::class.java).setAction(ApplicationClass.PLAYPAUSE)           // Binding старта/паузы аудио файла плейера
        val playPausePendingIntent = PendingIntent.getBroadcast(this, 0, playPauseIntent, PendingIntent.FLAG_IMMUTABLE) //  в Notification

        val nextIntent = Intent(this, ReceiverNotification::class.java).setAction(ApplicationClass.NEXT)                     //  Binding смещения вперёд аудио файла плейера
        val nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)           //  в Notification

        val previousIntent = Intent(this, ReceiverNotification::class.java).setAction(ApplicationClass.PREVIOUS)            //  Binding смещения назад аудио файла плейера
        val previousPendingIntent = PendingIntent.getBroadcast(this, 0, previousIntent, PendingIntent.FLAG_IMMUTABLE)  //  в Notification

        val closeIntent = Intent(this, ReceiverNotification::class.java).setAction(ApplicationClass.CLOSE)                  //  Binding закрытия
        val closePendingIntent = PendingIntent.getBroadcast(this, 0, closeIntent, PendingIntent.FLAG_IMMUTABLE)        //  в Notification

        val imgArt = DataSound().getImageArt(MainActivity.sound_list[MainActivity.sound_position].path)

        val notification = NotificationCompat.Builder(this, ApplicationClass.CHANNEL_ID.toString())
            .setContentTitle(MainActivity.sound_list[MainActivity.sound_position].name)
            .setContentText(MainActivity.sound_list[MainActivity.sound_position].author)
            .setSmallIcon(R.drawable.ic_treble_clef_black)
            .setLargeIcon(
                if (imgArt != null) BitmapFactory.decodeByteArray(imgArt, 0, imgArt.size)
                else BitmapFactory.decodeResource(resources,R.drawable.ic_treble_clef_black))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)

                //  Кнопки действий в Notification
            .addAction(R.drawable.ic_arrow_left,"Previous", previousPendingIntent)
            .addAction(
                if (MainActivity.isPlaing) R.drawable.ic_pause
                else R.drawable.ic_play, "Play", playPausePendingIntent)
            .addAction(R.drawable.ic_arrow_right,"Next", nextPendingIntent)
            .addAction(R.drawable.ic_close,"Exit", closePendingIntent)
            .build()

        startForeground(13, notification)
    }
}