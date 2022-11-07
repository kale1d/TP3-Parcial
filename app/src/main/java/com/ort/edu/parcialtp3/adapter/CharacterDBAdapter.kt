package com.ort.edu.parcialtp3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.fragments.favoritos
import com.ort.edu.parcialtp3.model.CharacterDB

/**
 * Adapter para los personajes que se muestran en la Home.
 * @param characterList es la lista de personajes que vamos a mostrar en Home
 * @param onCharacterClickedListener listener al cual se va a invocar cada vez que se seleccione un personaje de la lista
 */
class CharacterDBAdapter(
    private var characterList: List<CharacterDB>,
    favoritos: favoritos
) : RecyclerView.Adapter<CharacterDBViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDBViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_character, parent, false)
        return CharacterDBViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterDBViewHolder, position: Int) {
        // De la lista, obtengo el personaje basandome en la posicion de la celda en el recyclerview
        val character = characterList[position]

        // Se invoca al ViewHolder para que muestre los datos del personaje
        holder.bind(character)

    }

    override fun getItemCount() = characterList.size

    fun setItems(characterList: List<CharacterDB>) {
        this.characterList = characterList
    }
}