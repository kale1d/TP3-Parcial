package com.ort.edu.parcialtp3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.UserSession
import com.ort.edu.parcialtp3.adapter.CharacterDBAdapter
import com.ort.edu.parcialtp3.model.CharacterDB
import com.ort.edu.parcialtp3.repository.CharactersRepository
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [favoritos.newInstance] factory method to
 * create an instance of this fragment.
 */
class favoritos : Fragment() {
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var adapter: CharacterDBAdapter
    private lateinit var characterList: List<CharacterDB>
    private var spanCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var name = view.findViewById<TextView>(R.id.fav_title)
        name.text = "Hola ${UserSession.userName}, estos son tus personajes favoritos"


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        context?.let {
            charactersRepository = CharactersRepository.getInstance(it)
        }

        characterRecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        characterList = arrayListOf<CharacterDB>()

        lifecycleScope.launch {
            val layoutManager = GridLayoutManager(context, spanCount)
            characterList = charactersRepository.getAllCharacters()
            adapter = CharacterDBAdapter(characterList, this@favoritos)
            characterRecyclerView.layoutManager = layoutManager
            characterRecyclerView.adapter = adapter

            adapter.setItems(characterList)
            adapter.notifyDataSetChanged()
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment favoritos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            favoritos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}