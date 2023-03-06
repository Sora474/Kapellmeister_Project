package com.example.kapellmeister.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import kotlin.system.exitProcess

class ReceiverNotification: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        when(p1?.action){
            ApplicationClass.PLAYPAUSE ->  {
                if (MainActivity.isPlaing) DataSound().pauseSound()
                else DataSound().playSound()
            }
            ApplicationClass.NEXT      -> DataSound().moveSound(true)
            ApplicationClass.PREVIOUS  -> DataSound().moveSound(false)
            ApplicationClass.CLOSE     -> {
                MainActivity.soundService?.stopForeground(true)
                MainActivity.soundService = null
                exitProcess(1)
            }
        }
    }
}