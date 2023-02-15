package com.example.kapellmeister.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Datas.TimeFormat
import com.example.kapellmeister.Holders.SoundHolder
import com.example.kapellmeister.Pages.BindingClass
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.VModelSongUnitBinding

class SoundAdapter(private val context: Context, private val sound_list: ArrayList<SoundModel>): RecyclerView.Adapter<SoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
        return SoundHolder(VModelSongUnitBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: SoundHolder, position: Int) {
        //  Присвоение параметров sound_list к пользовательскому интерфейсу
        holder.name.text = sound_list[position].name
        holder.author.text = sound_list[position].author
        holder.time.text = TimeFormat(sound_list[position].time)
        Glide.with(context)
            .load(sound_list[position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef).centerCrop())
            .into(holder.img)

        //  Передача context
        holder.root.setOnClickListener{
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("sound_index", position)
            intent.putExtra("sound_class", "SoundAdapter")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return sound_list.size
    }
}