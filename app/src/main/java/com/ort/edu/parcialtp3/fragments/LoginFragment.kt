package com.ort.edu.parcialtp3.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ort.edu.parcialtp3.MainActivity
import com.ort.edu.parcialtp3.R
import com.ort.edu.parcialtp3.UserSession
import com.ort.edu.parcialtp3.dataStore
import com.ort.edu.parcialtp3.model.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class LoginFragment : Fragment() {

    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var continueButton: Button
    private lateinit var navController: NavController
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

    private val loginTextWatcher = object : TextWatcher {
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

        navController = findNavController()

        goToHome()

        continueButton.setOnClickListener {
            //guardo los valores en el data store
            lifecycleScope.launch(Dispatchers.IO) {
                saveValues(userEditText.text.toString(), passwordEditText.text.toString())
            }
            goToHome()
        }
    }


    private fun goToHome() {
        lifecycleScope.launch(Dispatchers.IO) {
            getUserData().collect { preferences ->
                withContext(Dispatchers.Main) {
                    if (preferences.name !== null && preferences.name.isNotEmpty()) {
                        navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(preferences.name))
                    } else {

                    }
                }
            }
        }
    }

    private fun getUserData() = requireContext().dataStore.data.map { preferences ->
        UserData(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            password = preferences[stringPreferencesKey("password")].orEmpty()
        )
    }

    private suspend fun saveValues(name: String, password: String) {
        requireContext().dataStore.edit { preferences ->
            preferences[stringPreferencesKey("name")] = name
            preferences[stringPreferencesKey("password")] = password
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        ((MainActivity)getActivity()).setDrawerUnlocked()
    }

}

private fun NavController.addOnDestinationChangedListener(listener: () -> Unit) {

}
