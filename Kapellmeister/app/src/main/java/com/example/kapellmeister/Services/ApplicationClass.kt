package com.example.kapellmeister.Services

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.os.Build
import androidx.core.app.NotificationManagerCompat

class ApplicationClass: Application() {
    companion object{
        const val CHANNEL_ID = 1001
        const val CHANNEL_NAME = "channal1_name"
        const val PLAYPAUSE = "play"
        const val NEXT = "next"
        const val PREVIOUS = "previous"
        const val CLOSE = "exit"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CHANNEL_ID.toString(), CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)    //  Регистрация менеджера для Notification
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}