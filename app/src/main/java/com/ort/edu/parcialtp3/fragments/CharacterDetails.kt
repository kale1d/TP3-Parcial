package com.ort.edu.parcialtp3.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.listener.OnCharacterClickedListener
import com.ort.edu.parcialtp3.model.Character
import com.ort.edu.parcialtp3.model.CharacterDB
import com.ort.edu.parcialtp3.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.jar.Attributes.Name

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterDetails : Fragment(), OnCharacterClickedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var characterRepository: CharactersRepository
    private lateinit var characterImage: ImageView
    private lateinit var estado: TextView
    private lateinit var nombre: TextView
    private lateinit var especie: TextView
    private lateinit var origen: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var character: Character


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character_details, container, false)

        return view
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            characterRepository = CharactersRepository.getInstance(it)
        }

        characterImage = view.findViewById(R.id.character_image_details)
        estado = view.findViewById(R.id.character_estado)
        nombre = view.findViewById(R.id.character_nombre)
        especie = view.findViewById(R.id.character_especie)
        origen = view.findViewById(R.id.character_origen)
        checkBox = view.findViewById(R.id.fav_check)
        arguments?.let {
            character = CharacterDetailsArgs.fromBundle(it).character

            estado.text = "Estado: ${character.status}"
            nombre.text = character.name
            especie.text = "Especie: ${character.species}"
            origen.text = "Origen: ${character.origin?.name}"
            Glide.with(this)
                .load(character.image)
                .into(characterImage)
            setCheckbox(checkBox, character.id)
        }

        onCharacterSelected(character)
    }

    private fun  setCheckbox(checkBox: CheckBox, id: Int) {
        lifecycleScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                val character = characterRepository.getCharacter(id)
                if(character !==null){
                    checkBox.isChecked = true
                }
            }
        }
    }
    override fun onCharacterSelected(character: Character) {

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        val characterDB = CharacterDB(
                            character.id,
                            character.name,
                            character.status,
                            character.image,
                            character.species
                        )
                        characterRepository.addCharacter(characterDB)
                    }
                }
            } else {
                lifecycleScope.launch {
                    val characterDB = CharacterDB(
                        character.id,
                        character.name,
                        character.status,
                        character.image,
                        character.species
                    )
                    characterRepository.removeCharacter(characterDB)

                }
            }

        }
    }
}