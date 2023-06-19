package com.example.kapellmeister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Adapters.SoundSelectionAdapter
import com.example.kapellmeister.Datas.DataCollection
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.databinding.ActivityMainBinding
import com.example.kapellmeister.databinding.ActivityPlayerBinding
import com.example.kapellmeister.databinding.ActivitySelectionBinding
import java.lang.Exception

class SelectionActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivitySelectionBinding
    private lateinit var soundAdapter: SoundSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        BindingClass.btnChangeSound.setOnClickListener(){
            if(intent.getStringExtra("operation") == "add") DataCollection().changeSoundInCollection(BindingClass.root.context, intent.getStringExtra("collection_name")!!, MainActivity.select_sound_list,true)
            else  DataCollection().changeSoundInCollection(BindingClass.root.context, intent.getStringExtra("collection_name")!!, MainActivity.select_sound_list,false)
            MainActivity.select_sound_list.clear()
            finish()
        }

        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(20)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = LinearLayoutManager(BindingClass.root.context)
        getCollectionArray(intent.getStringExtra("collection_name")!!)
        soundAdapter = if(intent.getStringExtra("operation") == "add") SoundSelectionAdapter(BindingClass.root.context,MainActivity.initial_list)
        else  SoundSelectionAdapter(BindingClass.root.context, MainActivity.collection_sound_list)
        BindingClass.rvList.adapter = soundAdapter

    }
    private fun initializeLayout() /* Подгрузка данных в Activity */ {
        BindingClass = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)

        if(intent.getStringExtra("operation") == "add") {
            BindingClass.btnChangeSound.setImageResource(R.drawable.ic_add_new)
            BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + MainActivity.initial_list.size.toString()}
        else  {
            BindingClass.btnChangeSound.setImageResource(R.drawable.ic_delete)
            BindingClass.tvTotalSound.text =  getString(R.string.total_sound) + MainActivity.collection_sound_list.size.toString()}

        MainActivity.select_sound_list.clear()
    }
    private fun getCollectionArray(name: String) /* Получение ПлейЛиста */ {
        var temp_list: ArrayList<String> = DataCollection().readSoundCollection(BindingClass.root.context,"$name")
        MainActivity.collection_sound_list.clear()
        temp_list.forEach(){
            val tempId = it
            try{ MainActivity.collection_sound_list.add(MainActivity.initial_list.find {it.path == tempId }!!) } catch (e: Exception){}
        }
    }
}