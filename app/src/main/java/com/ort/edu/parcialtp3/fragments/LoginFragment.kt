package com.ort.edu.parcialtp3.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.ort.edu.parcialtp3.MainActivity
import com.ort.edu.parcialtp3.R

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        ((MainActivity)getActivity()).setDrawerLocked()

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navController = findNavController()
        val continueButton = view.findViewById<Button>(R.id.login_button)
        userEditText = view.findViewById(R.id.username_input)
        passwordEditText = view.findViewById(R.id.password_input)

        continueButton.setOnClickListener {

            // Navego hacia la home
            if(!userEditText.text.isEmpty() && !passwordEditText.text.isEmpty()){
                navController.navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                )
            }else{
//                val builder = AlertDialog.Builder(context)
//                //set title for alert dialog
//                builder.setTitle("Incompleto")
//                //set message for alert dialog
//                builder.setMessage("Mail y contrasenia deben contener algo")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        ((MainActivity)getActivity()).setDrawerUnlocked()
    }

}