package com.ort.edu.parcialtp3.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
   private lateinit var continueButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        continueButton = view.findViewById<Button>(R.id.login_button)
        userEditText = view.findViewById(R.id.username_input)
        passwordEditText = view.findViewById(R.id.password_input)

        userEditText.addTextChangedListener(loginTextWatcher)
        passwordEditText.addTextChangedListener(loginTextWatcher)

        return view;
    }
 private val loginTextWatcher = object: TextWatcher {
     override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

     override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         val user_name = userEditText.text.toString().trim()
         val password = passwordEditText.text.toString().trim()

         continueButton.isEnabled = user_name.isNotEmpty() && password.isNotEmpty()
     }

     override fun afterTextChanged(p0: Editable?) {}

 }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()



        continueButton.setOnClickListener {
            // Navego hacia la home
                navController.navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        ((MainActivity)getActivity()).setDrawerUnlocked()
    }

}