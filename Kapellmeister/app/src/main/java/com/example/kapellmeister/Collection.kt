package com.example.kapellmeister

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kapellmeister.Adapters.CollectionAdapter
import com.example.kapellmeister.Datas.CollectionModel
import com.example.kapellmeister.Datas.DataCollection
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.databinding.ActivityCollectionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.system.exitProcess


class Collection : AppCompatActivity() {
    lateinit var BindingClass : ActivityCollectionBinding
    private lateinit var collectionAdapter: CollectionAdapter
    lateinit var toogle : ActionBarDrawerToggle

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        getCollectionArray(intent.getStringExtra("collection_name")!!)

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = GridLayoutManager(BindingClass.root.context,3)
        collectionAdapter = CollectionAdapter(BindingClass.root.context,MainActivity.collection_sound_list)
        BindingClass.rvList.adapter = collectionAdapter

        BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + DataSound().getSizeSoundList(MainActivity.collection_sound_list).toString()

        ////////////////////    For left_menu
        BindingClass.nvGeneric.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.generic_left_menu_exit      -> {   // Обработчик выхода из приложения
                    val builder = MaterialAlertDialogBuilder(this)
                    builder.setTitle(getString(R.string.exit))
                        .setMessage(getString(R.string.alert_dialog_exit_question))
                        .setPositiveButton(getString(R.string.yes)){ temp_atribut, _ ->
                            if(MainActivity.soundService != null){
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
        BindingClass = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)
        if(intent.getStringExtra("collection_name") == "Favorite") BindingClass.collectionName.text = "Избранное"
        else BindingClass.collectionName.text = intent.getStringExtra("collection_name")

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
    override fun onResume() {
        super.onResume()
        getCollectionArray(intent.getStringExtra("collection_name")!!)
        BindingClass.rvList.adapter = collectionAdapter
        BindingClass.tvTotalSound.text =  getString(R.string.total_collection) + MainActivity.collection_sound_list.size.toString()
    }
    private fun getCollectionArray(name: String) /* Получение ПлейЛиста */ {
        var temp_list: ArrayList<String> = DataCollection().readSoundCollection(BindingClass.root.context,"$name")
        MainActivity.collection_sound_list.clear()
        temp_list.forEach(){MainActivity.collection_sound_list.add(MainActivity.initial_list[it.toInt()])}
    }
}