package com.example.kapellmeister.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Collection
import com.example.kapellmeister.Datas.CollectionModel
import com.example.kapellmeister.Datas.DataCollection
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Holders.CollectionListHolder
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.VModelCollectionListUnitBinding
import com.example.kapellmeister.databinding.VModelCollectionUnitBinding

class CollectionListAdapter(private val context: Context, private val collection_list: ArrayList<CollectionModel>): RecyclerView.Adapter<CollectionListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionListHolder {
        return CollectionListHolder(VModelCollectionListUnitBinding.inflate(LayoutInflater.from(context), parent, false))
    }



    override fun onBindViewHolder(holder: CollectionListHolder, position: Int) {
        //  Присвоение параметров sound_list к пользовательскому интерфейсу
        if(collection_list[position].name == "Favorite") holder.name.text = "Избранное"
        else holder.name.text = collection_list[position].name
        holder.name.isSelected = true
        Glide.with(context)
            .load(collection_list[position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_black).centerCrop())
            .into(holder.img)

        //  Передача context
        holder.root.setOnClickListener {
            getIntent(ref = "CollectionList", collection_list[position].name)
        }
    }

    override fun getItemCount(): Int {
        return collection_list.size
    }

    fun getIntent(ref: String, pos: String) /* Параметры запуска PlayerActivity */ {
        val intent = Intent(context, Collection::class.java)
        intent.putExtra("collection_name", pos)
        intent.putExtra("sound_class", ref)
        ContextCompat.startActivity(context, intent, null)
    }
}