package com.ort.edu.parcialtp3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.listener.OnCharacterClickedListener
import com.ort.edu.parcialtp3.model.Character

/**
 * Adapter para los personajes que se muestran en la Home.
 * @param characterList es la lista de personajes que vamos a mostrar en Home
 * @param onCharacterClickedListener listener al cual se va a invocar cada vez que se seleccione un personaje de la lista
 */
class CharacterAdapter(
    private var characterList: List<Character>,
    private val onCharacterClickedListener: OnCharacterClickedListener
) : RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        // De la lista, obtengo el personaje basandome en la posicion de la celda en el recyclerview
        val character = characterList[position]

        // Se invoca al ViewHolder para que muestre los datos del personaje
        holder.bind(character)

        // Establezco un click listener en el itemview del holder. Esto seria, la vista entera del elemento {position}
        // de la lista
        holder.itemView.setOnClickListener {
            onCharacterClickedListener.onCharacterSelected(character)
        }
    }

    override fun getItemCount() = characterList.size

    fun setItems(characterList: List<Character>) {
        this.characterList = characterList
    }
}