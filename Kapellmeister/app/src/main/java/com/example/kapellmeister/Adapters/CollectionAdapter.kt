package com.example.kapellmeister.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Holders.CollectionHolder
import com.example.kapellmeister.Holders.CollectionListHolder
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.VModelCollectionUnitBinding

class CollectionAdapter(private val context: Context, private val sound_list: ArrayList<SoundModel>): RecyclerView.Adapter<CollectionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        return CollectionHolder(VModelCollectionUnitBinding.inflate(LayoutInflater.from(context), parent, false))
    }



    override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
        //  Присвоение параметров sound_list к пользовательскому интерфейсу
        holder.name.text = sound_list[position].name
        holder.name.isSelected = true
        Glide.with(context)
            .load(sound_list[position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_black).centerCrop())
            .into(holder.img)

        //  Передача context
        holder.root.setOnClickListener {
            getIntent(ref = "CollectionList", position)

        }
    }

    override fun getItemCount(): Int {
        return sound_list.size
    }

    fun getIntent(ref: String, pos: Int) /* Параметры запуска PlayerActivity */ {
        val intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra("sound_index", pos)
        intent.putExtra("sound_class", ref)
        ContextCompat.startActivity(context, intent, null)
    }
}