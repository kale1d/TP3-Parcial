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
import kotlinx.coroutines.launch
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
    private lateinit var character:Character


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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_details, container, false)
//        val image = view.findViewById<ImageView>(R.id.imageView)
//
//        Glide.with(this).load(args.character.image).into(image)
//
        return view
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterImage = view.findViewById(R.id.character_image_details)
        estado = view.findViewById(R.id.character_estado)
        nombre = view.findViewById(R.id.character_nombre)
        especie = view.findViewById(R.id.character_especie)
        origen = view.findViewById(R.id.character_origen)
        checkBox = view.findViewById(R.id.fav_check)
        arguments?.let {
            character = CharacterDetailsArgs.fromBundle(it).character

            estado.text = "Estado: ${character.status}"
            nombre.text =  character.name
            especie.text = "Especie: ${character.species}"
            origen.text = "Origen: ${character.origin?.name}"
            Glide.with(this)
                .load(character.image)
                .into(characterImage)
        }

        onCharacterSelected(character)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CharacterDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCharacterSelected(character: Character) {
        context?.let{
            characterRepository = CharactersRepository.getInstance(it)
        }
        checkBox.setOnClickListener {
            if (checkBox.isChecked){
                lifecycleScope.launch{
                    characterRepository.addCharacter(character as CharacterDB)

                }
            }
            else {
                lifecycleScope.launch{
                    characterRepository.removeCharacter(character as CharacterDB)

                }
            }

        }
    }
}