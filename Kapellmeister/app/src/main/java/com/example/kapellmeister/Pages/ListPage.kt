package com.example.kapellmeister.Pages


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataPermission
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.ActivityMainBinding
import com.example.kapellmeister.databinding.FragmentListPageBinding
import java.io.File

lateinit var BindingClass : FragmentListPageBinding
class ListPage : Fragment(R.layout.fragment_list_page) {
    private lateinit var soundAdapter: SoundAdapter
    companion object{
        lateinit var sound_list : ArrayList<SoundModel>
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentListPageBinding.inflate(inflater)

        sound_list = getSoundAll()  //  Получение ПлейЛиста

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        soundAdapter = SoundAdapter(BindingClass.root.context, sound_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSoundCount.text =  soundAdapter.itemCount.toString()

        return BindingClass.root
    }
}

private fun getSoundAll() : ArrayList<SoundModel> /* Получение всех аудиофайлов из внешнего хранилища */ {
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val temp_list = ArrayList<SoundModel>()
    val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATE_ADDED,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.ALBUM_ID
    )

    //  Регистрация поисковика
    val cursor = BindingClass.root.context.contentResolver.query(uri , projection, selection,
                                                    null, MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
    if (cursor == null){
        Toast.makeText(BindingClass.root.context,"Something wrong",Toast.LENGTH_SHORT)
    } else if(!cursor.moveToFirst()){
        Toast.makeText(BindingClass.root.context,"Sound not found",Toast.LENGTH_SHORT)
    } else {
        do {
            //  Присвоение данных аудиофайла
            val idC     = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
            val nameC   = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))

            val authorC = if(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)) == "<unknown>")
                            "Неизвестный исполнитель"
                          else  cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

            val albumC  = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
            val timeC   = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
            val pathC   = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))

            val albumIdC   = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)).toString()
            val uriArt = Uri.parse("content://media/external/audio/albumart")
            val uriArtC = Uri.withAppendedPath(uriArt,albumIdC).toString()

            val sound = SoundModel(idC, nameC, authorC, albumC, timeC, pathC, uriArtC)
            val file = File(sound.path)

            if(file.exists()){
                temp_list.add(sound)
            }
        }while (cursor.moveToNext())
        cursor.close()
    }
    return temp_list
}