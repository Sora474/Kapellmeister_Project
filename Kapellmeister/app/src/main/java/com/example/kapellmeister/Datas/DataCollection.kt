package com.example.kapellmeister.Datas

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.Pages.NowPlaying
import com.example.kapellmeister.PlayerActivity
import java.util.concurrent.TimeUnit


data class CollectionModel(val name:String, val artUri:String){}
class DataCollection(){

    fun readSoundCollection(context: Context, name: String): ArrayList<String> /* Чтение коллекции в DataStorage */ {
        val editor = context.getSharedPreferences("Collection$name", Context.MODE_PRIVATE)
        val tempArray = editor.getStringSet("Sound$name", null)
        var data: ArrayList<String> = ArrayList()
        tempArray!!.forEach(){data.add(it)}
        return data
    }

    fun changeStatusSoundFavorite(context: Context) /* Изменение коллекции 'Избранное' в DataStorage */ {
        val tempArray: ArrayList<String> = readSoundCollection(context,"Favorite")
        var tempId = MainActivity.sound_list[MainActivity.sound_position].path

        if (tempArray.contains(tempId)) {
            tempArray.remove(tempId)

            val editor = context.getSharedPreferences("CollectionFavorite", Context.MODE_PRIVATE).edit()
            editor.putStringSet("SoundFavorite", tempArray.toSortedSet())
            editor.apply()
        } else {
            tempArray.add(tempId)

            val editor = context.getSharedPreferences("CollectionFavorite", Context.MODE_PRIVATE).edit()
            editor.putStringSet("SoundFavorite", tempArray.toSortedSet())
            editor.apply()
        }
    }

    fun addSoundCollection(context: Context, name: String) /* Добавление коллекции в DataStorage */ {
        val tempArray: ArrayList<String> = ArrayList()

        val editor = context.getSharedPreferences("Collection$name", Context.MODE_PRIVATE).edit()
        editor.putStringSet("Sound$name", tempArray.toSortedSet())
        editor.apply()

        addInCollectionMain(context,name)
    }

    private fun addInCollectionMain(context: Context, name: String) /* Добавление коллекции в CollectionMain в DataStorage */ {
        val tempArray: ArrayList<String> = readSoundCollection(context,"Main")
        tempArray.add(name)

        val editor = context.getSharedPreferences("CollectionMain", Context.MODE_PRIVATE).edit()
        editor.putStringSet("SoundMain", tempArray.toSortedSet())
        editor.apply()
    }
}



