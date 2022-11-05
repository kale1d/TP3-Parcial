package com.ort.edu.parcialtp3.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.adapter.CharacterAdapter
import com.ort.edu.parcialtp3.dataStore
import com.ort.edu.parcialtp3.model.Character
import com.ort.edu.parcialtp3.model.CharacterData
import com.ort.edu.parcialtp3.model.UserData
import com.ort.edu.parcialtp3.services.ApiServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var characterList: List<Character>
    private lateinit var searchView: SearchView

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterRecyclerView = view.findViewById(R.id.characterRecyclerView)
        characterList = arrayListOf<Character>()
        val textHome = view.findViewById<TextView>(R.id.homeTextView)

        getCharacters()
        // Configuro el recyclerview y le paso un Adapter
        // val layoutManager = LinearLayoutManager(context)
        val layoutManager = GridLayoutManager(context, 2)
        characterRecyclerView.layoutManager = layoutManager
        characterRecyclerView.adapter = CharacterAdapter(characterList)

        // Configuro el searchview
        searchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    getCharactersByName(newText)
                }
                return false
            }
        })
    }



    fun getCharacters() {
        val service = ApiServiceBuilder.create()

        service.getCharacters().enqueue(object : Callback<CharacterData> {
            override fun onResponse(
                call: Call<CharacterData>,
                response: Response<CharacterData>
            ) {
                if (response.isSuccessful) {
                    characterList = response.body()!!.results
                    characterRecyclerView.adapter = CharacterAdapter(characterList)
                    // Para que funcione el onclick y abra la info de un character,
                    // characterRecyclerView.adapter = CharacterAdapter(characterList, this)
                    // ver video https://www.youtube.com/watch?v=K5YnTvsVPRk
                    //characterRecyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<CharacterData>, t: Throwable) {
                Log.e("Error", t.toString())
            }
        })
    }

    fun getCharactersByName(name: String) {
        val service = ApiServiceBuilder.create()

        service.getCharactersByName(name).enqueue(object : Callback<CharacterData> {
            override fun onResponse(
                call: Call<CharacterData>,
                response: Response<CharacterData>
            ) {
                if (response.isSuccessful) {
                    characterList = response.body()!!.results
                    characterRecyclerView.adapter = CharacterAdapter(characterList)
                    // characterRecyclerView.adapter = CharacterAdapter(characterList, this)
                }
            }

            override fun onFailure(call: Call<CharacterData>, t: Throwable) {
                Log.e("Error", t.toString())
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}