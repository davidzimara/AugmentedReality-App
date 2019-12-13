package com.example.lernapp.UI

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lernapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity)
            .setActionBarTitle("Einstellungen")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val email = user.email

            val uid = user.uid

            user_name.text = email
        }

        updateEmailButton.setOnClickListener {
            updateEmail()
        }

        updatePasswordButton.setOnClickListener {
            updatePassword()
        }
    }

    fun updateEmail() {

        val user = FirebaseAuth.getInstance().currentUser

        editText = view!!.findViewById<EditText>(R.id.nameEmail)

        val email = editText.text.toString().trim()

        if (email.isEmpty()) {
            editText.error = "Bitte geben sie eine E-Mail-Adresse an."
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(nameEmail.text.toString()).matches()) {
            editText.error = "Bitte geben Sie eine gültige Email Adresse an."
            editText.requestFocus()
            return
        }

        user?.updateEmail(email)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "E-Mail wurde geändert.", Toast.LENGTH_LONG).show()
                    //to refresh the email on the fragment screen, otherwise it`ll be the old email
                    user_name.text = email
                    editText.text.clear()
                } else {
                    editText.error = "Änderung der Email fehlgeschlagen."
                }
            }
    }

    fun updatePassword() {

        editText = view!!.findViewById<EditText>(R.id.namePassword)

        val password = editText.text.toString().trim()

        val user = FirebaseAuth.getInstance().currentUser

        if (password.isEmpty()) {
            editText.error = "Bitte geben Sie ein Passwort an."
            return
        }

        user?.updatePassword(password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Passowrt wurde geändert.", Toast.LENGTH_LONG)
                        .show()
                    editText.text.clear()
                } else {
                    editText.error = "Verwenden Sie bitte mehr als 6 Zeichen."
                }
            }
    }
}
