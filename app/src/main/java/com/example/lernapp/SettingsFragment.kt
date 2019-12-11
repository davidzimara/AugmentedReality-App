package com.example.lernapp


import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNREACHABLE_CODE")
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

        val ud = 12030440
        val farbe = "rot"

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url

            val email = user.email

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            user_name.setText(email)
        }

        updateEmailButton.setOnClickListener {
            updateEmail()
        }

        updatePasswordButton.setOnClickListener {
            updatePassword()
        }
    }

    fun updateEmail (){

        val user = FirebaseAuth.getInstance().currentUser

        editText = view!!.findViewById<EditText>(R.id.nameEmail)

        val email = editText.text.toString().trim()

        if (email.isEmpty()) {
            editText.error ="Bitte geben sie eine E-Mail-Adresse an."
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(nameEmail.text.toString()).matches()) {
            editText.error = "Bitte geben Sie eine gültige Email Adresse an."
            editText.requestFocus()
            return
        }

        user?.updateEmail(email)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this.context, "E-Mail wurde geändert.", Toast.LENGTH_LONG).show()
                        //to refresh the email on the fragment screen, otherwise it`ll be the old email
                        user_name.setText(email)
                        editText.text.clear()
                    } else {
                        editText.error="Änderung der Email fehlgeschlagen."
                    }
        }
    }

    fun updatePassword() {

        editText = view!!.findViewById<EditText>(R.id.namePassword)

        val password = editText.text.toString().trim()

        val user = FirebaseAuth.getInstance().currentUser

        if (password.isEmpty()) {
            editText.error ="Bitte geben Sie ein Passwort an."
            return
        }

        user?.updatePassword(password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Passowrt wurde geändert.", Toast.LENGTH_LONG).show()
                    editText.text.clear()
                } else {
                    editText.error="Verwenden Sie bitte mehr als 6 Zeichen."
                }
            }
    }
}
