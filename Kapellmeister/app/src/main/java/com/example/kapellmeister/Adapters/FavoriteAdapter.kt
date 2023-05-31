package com.example.kapellmeister.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kapellmeister.Datas.DataSound
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Holders.AuthorHolder
import com.example.kapellmeister.Holders.FavoriteHolder
import com.example.kapellmeister.Holders.SoundHolder
import com.example.kapellmeister.MainActivity
import com.example.kapellmeister.PlayerActivity
import com.example.kapellmeister.R
import com.example.kapellmeister.databinding.VModelAuthorUnitBinding
import com.example.kapellmeister.databinding.VModelFavoriteUnitBinding
import com.example.kapellmeister.databinding.VModelSongUnitBinding

class FavoriteAdapter(private val context: Context, private val sound_list: ArrayList<SoundModel>): RecyclerView.Adapter<FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(VModelFavoriteUnitBinding.inflate(LayoutInflater.from(context), parent, false))
    }



    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        //  Присвоение параметров sound_list к пользовательскому интерфейсу
        holder.name.text = sound_list[position].name
        Glide.with(context)
            .load(sound_list[position].artUri)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_treble_clef_black).centerCrop())
            .into(holder.img)

        //  Передача context
        holder.root.setOnClickListener {
            when {
                sound_list == MainActivity.favorite_sound_list -> getIntent(ref = "FavoriteSoundList", position)
            }

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