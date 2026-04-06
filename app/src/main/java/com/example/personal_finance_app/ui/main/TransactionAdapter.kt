package com.example.personal_finance_app.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_app.R
import com.example.personal_finance_app.data.local.TransactionEntity

class TransactionAdapter(
    private val list: List<TransactionEntity>,
    private val onDeleteClick: (TransactionEntity) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amount = view.findViewById<TextView>(R.id.tv_amount)
        val category = view.findViewById<TextView>(R.id.tv_category)
        val note = view.findViewById<TextView>(R.id.tv_note)
        val deleteBtn = view.findViewById<Button>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.amount.text = "₹${item.amount}"
        holder.category.text = item.category
        holder.note.text = item.note

        holder.amount.setTextColor(
            if (item.type == "income") Color.parseColor("#00B894")
            else Color.parseColor("#D63031")
        )

        holder.deleteBtn.setOnClickListener {
            onDeleteClick(item)
        }
    }
}