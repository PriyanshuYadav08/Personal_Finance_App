package com.example.personal_finance_app.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_app.R

class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_transactions)

        // TODO:
        // Setup RecyclerView Adapter
        // Connect Room DB data
    }
}