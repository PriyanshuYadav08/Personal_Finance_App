package com.example.personal_finance_app.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.personal_finance_app.R
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val nameText = view.findViewById<TextView>(R.id.user_name_text)

        val name = user?.displayName ?: "User"
        nameText.text = "Hi, $name 👋"
    }
}