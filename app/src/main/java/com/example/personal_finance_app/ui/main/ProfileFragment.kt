package com.example.personal_finance_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.personal_finance_app.ui.main.AuthPageActivity
import com.example.personal_finance_app.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        val nameText = view.findViewById<TextView>(R.id.profile_name)
        val emailText = view.findViewById<TextView>(R.id.profile_email)

        nameText.text = user?.displayName ?: "User"
        emailText.text = user?.email ?: "No Email"

        val logoutBtn = view.findViewById<Button>(R.id.logout_btn)

        logoutBtn.setOnClickListener {

            val googleSignInClient = GoogleSignIn.getClient(
                requireContext(),
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            )

            googleSignInClient.signOut().addOnCompleteListener {

                requireContext()
                    .getSharedPreferences("auth_prefs", 0)
                    .edit {
                        clear()
                    }

                startActivity(Intent(requireContext(), AuthPageActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}