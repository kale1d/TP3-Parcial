package com.ort.edu.parcialtp3.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.model.CharacterDB

class CharacterDBViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView
    private val status: TextView
    private val image: ImageView

    init {
        name = itemView.findViewById(R.id.dataCharacter_name)
        status = itemView.findViewById(R.id.dataCharacter_status)
        image = itemView.findViewById(R.id.dataCharacter_image)
    }

    fun bind(character: CharacterDB) {
        name.text = character.name
        status.text = character.status

        Glide.with(itemView)
            .load(character.image)
            .into(image)
    }
}