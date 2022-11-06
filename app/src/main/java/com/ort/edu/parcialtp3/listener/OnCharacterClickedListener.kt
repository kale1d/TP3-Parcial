package com.ort.edu.parcialtp3.listener
import com.ort.edu.parcialtp3.model.Character
/**
 * Esta interfaz sera utilizada para ser implementada cuando se quiera manejar la seleccion de un personaje en una clase
 */
interface OnCharacterClickedListener {

    /**
     * Se invoca cuando se selecciona un personaje de la lista dinamica characterRecyclerView
     */
    fun onCharacterSelected(character: Character)
}