package com.example.kapellmeister

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.databinding.ActivityAuthorListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.system.exitProcess

class AuthorList : AppCompatActivity() {
    lateinit var BindingClass : ActivityAuthorListBinding
    private lateinit var soundAdapter: SoundAdapter
    lateinit var toogle : ActionBarDrawerToggle

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        var temp_list = ArrayList<SoundModel>()
        MainActivity.initial_list.forEach{if (it.author == intent.getStringExtra("author_name")) temp_list.add(it)}  //  Получение ПлейЛиста
        MainActivity.author_sound_list = temp_list //  Преобразвание ПлейЛиста

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        soundAdapter = SoundAdapter(BindingClass.root.context,MainActivity.author_sound_list)
        BindingClass.rvList.adapter = soundAdapter

        BindingClass.tvTotalSound.text =  getString(R.string.total_author_sound) + DataSound().getSizeSoundList(MainActivity.author_sound_list).toString()

        ////////////////////    For left_menu
        BindingClass.nvGeneric.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.generic_left_menu_exit      -> {   // Обработчик выхода из приложения
                    val builder = MaterialAlertDialogBuilder(this)
                    builder.setTitle(getString(R.string.exit))
                        .setMessage(getString(R.string.alert_dialog_exit_question))
                        .setPositiveButton(getString(R.string.yes)){ temp_atribut, _ ->
                            if(MainActivity.soundService != null){
                                MainActivity.soundService!!.audioManager.abandonAudioFocus(MainActivity.soundService)
                                MainActivity.soundService?.stopForeground(true)
                                MainActivity.soundService?.mediaPlayer?.release()
                                MainActivity.soundService = null
                            }
                            exitProcess(0)
                        }
                        .setNegativeButton(getString(R.string.no)){ dialog, _ ->
                            dialog.dismiss()
                        }

                    val customDialog = builder.create()
                    customDialog.show()
                    customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.ChapmanRed))
                    customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.ChapmanRed))
                }
            }
            true
        }
    }

    private fun initializeLayout(){
        BindingClass = ActivityAuthorListBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)
        BindingClass.authorName.text = intent.getStringExtra("author_name")

        ////////////////////    For left_menu
        toogle = ActionBarDrawerToggle(this, BindingClass.root , R.string.open, R.string.close)
        BindingClass.root.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean /* Обработчик подтверждения нажатия в left_menu */{
        if(toogle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}