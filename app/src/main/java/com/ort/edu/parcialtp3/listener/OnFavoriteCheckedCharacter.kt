package com.ort.edu.parcialtp3.listener

import com.ort.edu.parcialtp3.model.CharacterDB

interface OnFavoriteCheckedCharacter {
    fun onFavoriteCharacter(character: CharacterDB)
}