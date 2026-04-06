package com.example.personal_finance_app.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_app.R
import com.example.personal_finance_app.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_transactions)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val dao = AppDatabase.getDatabase(requireContext()).transactionDao()

        dao.getAllTransactions().observe(viewLifecycleOwner) { list ->
            recycler.adapter = TransactionAdapter(list) { item ->

                CoroutineScope(Dispatchers.IO).launch {
                    dao.delete(item)
                }
            }
        }

        val emptyText = view.findViewById<TextView>(R.id.empty_text)

        dao.getAllTransactions().observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                emptyText.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            } else {
                emptyText.visibility = View.GONE
                recycler.visibility = View.VISIBLE
            }
            recycler.adapter = TransactionAdapter(list) { item ->
                CoroutineScope(Dispatchers.IO).launch {
                    dao.delete(item)
                }
            }
        }
    }
}