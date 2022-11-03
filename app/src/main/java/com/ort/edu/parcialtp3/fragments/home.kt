package com.ort.edu.parcialtp3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.services.ApiServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
        obtenerCharacters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun obtenerCharacters(){
        val service = ApiServiceBuilder.create()

        service.getCharacters().enqueue(object : Callback<ArrayList<com.ort.edu.parcialtp3.model.Character>> {

            override fun onResponse(
                call: Call<ArrayList<com.ort.edu.parcialtp3.model.Character>>,
                response: Response<ArrayList<com.ort.edu.parcialtp3.model.Character>>
            ) {
                if (response.isSuccessful) {
                    val lista = response.body();
                    var size = lista?.size ?: 1;
                    val data = lista?.get(size - 1);
                    var nombre = view?.findViewById<TextView>(R.id.nombre)
                    var especie = view?.findViewById<TextView>(R.id.especie)
                    if (nombre != null) {
                        nombre.text = data?.name.toString()
                    }
                    if (especie != null) {
                        especie.text = data?.species.toString()
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<com.ort.edu.parcialtp3.model.Character>>, t: Throwable) {
                Log.e("Ejemplo", t.toString())
            }

        })
    }


private fun <T> Call<T>.enqueue(callback: Callback<ArrayList<T>>) {

}

companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
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