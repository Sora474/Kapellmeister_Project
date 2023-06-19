package com.example.kapellmeister.Pages

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kapellmeister.Adapters.CollectionAdapter
import com.example.kapellmeister.Adapters.CollectionListAdapter
import com.example.kapellmeister.Datas.CollectionModel
import com.example.kapellmeister.Datas.DataCollection
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.FragmentCollectionListPageBinding
import com.example.kapellmeister.databinding.ModelDialogAddCollectionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.system.exitProcess

private lateinit var BindingClass : FragmentCollectionListPageBinding
class CollectionListPage : Fragment() {
    private lateinit var collectionAdapter: CollectionListAdapter
    private var collection_list: ArrayList<CollectionModel> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BindingClass = FragmentCollectionListPageBinding.inflate(inflater)
        BindingClass.btnAddCollection.setOnClickListener(){
           customAlertDialog()
        }

        getCollectionArray()


        BindingClass.rvList.setHasFixedSize(true)
        BindingClass.rvList.setItemViewCacheSize(10)

        // Настройка адаптера
        BindingClass.rvList.layoutManager = GridLayoutManager(BindingClass.root.context,3)
        collectionAdapter = CollectionListAdapter(BindingClass.root.context, collection_list)
        BindingClass.rvList.adapter = collectionAdapter

        BindingClass.tvTotalSound.text =  getString(R.string.total_collection) + collection_list.size.toString()

        return BindingClass.root
    }

    override fun onResume() {
        super.onResume()
        getCollectionArray()
        BindingClass.rvList.adapter = collectionAdapter
        BindingClass.tvTotalSound.text =  getString(R.string.total_collection) + collection_list.size.toString()
    }

    private fun getCollectionArray() /* Получение ПлейЛиста */ {
        var temp_list: ArrayList<String> = DataCollection().readSoundCollection(BindingClass.root.context,"Main")
        collection_list.clear()
        temp_list.forEach(){ collection_list.add(CollectionModel(it,
            if(DataCollection().readSoundCollection(BindingClass.root.context,it).isNotEmpty()){
                val tempId = DataCollection().readSoundCollection(BindingClass.root.context,it)[0]
                MainActivity.initial_list.find { it.path == tempId}!!.artUri
            }
            else getString(R.string.error_unknown)
        ))
        }
    }

    private fun customAlertDialog() /* Параметры создания новой Collection */ {
        val customDialog = LayoutInflater.from(BindingClass.root.context).inflate(R.layout.model_dialog_add_collection, BindingClass.root, false)
        val binder = ModelDialogAddCollectionBinding.bind(customDialog)
        val builder = MaterialAlertDialogBuilder(BindingClass.root.context)
        builder.setView(customDialog)
            .setTitle(getString(R.string.alert_dialog_add_collection))
            .setPositiveButton(getString(R.string.add)){ dialog, _ ->
                val collection_name = binder.tieCollectionName.text.toString()
                if (collection_name.isNotEmpty()){
                    val CollectionMain: ArrayList<String> = DataCollection().readSoundCollection(BindingClass.root.context, "Main")
                    if (CollectionMain.contains(collection_name)){
                        Toast.makeText(BindingClass.root.context,getString(R.string.notification_collection_already_exists),Toast.LENGTH_SHORT).show()
                    }
                    else{
                        DataCollection().addCollection(BindingClass.root.context, collection_name)
                        dialog.dismiss()
                        onResume()
                    }
                }else Toast.makeText(BindingClass.root.context,getString(R.string.notification_collection_еmpty_name_is_not_allowed),Toast.LENGTH_SHORT).show()
            }
            .show()
    }
}