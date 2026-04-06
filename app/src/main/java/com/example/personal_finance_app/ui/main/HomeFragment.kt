package com.example.personal_finance_app.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.personal_finance_app.R
import com.example.personal_finance_app.data.local.AppDatabase
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val nameText = view.findViewById<TextView>(R.id.user_name_text)

        val name = user?.displayName ?: "User"
        nameText.text = "Hi, $name 👋"

        val balanceTv = view.findViewById<TextView>(R.id.tv_balance)
        val incomeTv = view.findViewById<TextView>(R.id.tv_income)
        val expenseTv = view.findViewById<TextView>(R.id.tv_expense)

        val dao = AppDatabase.getDatabase(requireContext()).transactionDao()

        // Observe Income
        dao.getTotalIncome().observe(viewLifecycleOwner) { income ->

            val incomeValue = income ?: 0.0
            incomeTv.text = "Income\n₹$incomeValue"

            // Observe Expense inside (to calculate balance)
            dao.getTotalExpense().observe(viewLifecycleOwner) { expense ->

                val expenseValue = expense ?: 0.0
                expenseTv.text = "Expense\n₹$expenseValue"

                val balance = incomeValue - expenseValue
                balanceTv.text = "₹$balance"
            }
        }

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_goal)
        val goalText = view.findViewById<TextView>(R.id.tv_goal_status)
        val setGoalBtn = view.findViewById<Button>(R.id.btn_set_goal)
        val prefs = requireContext().getSharedPreferences("goal_prefs", 0)

        setGoalBtn.setOnClickListener {
            val editText = EditText(requireContext())

            AlertDialog.Builder(requireContext())
                .setTitle("Set Savings Goal")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val goal = editText.text.toString().toIntOrNull() ?: 0
                    prefs.edit().putInt("goal", goal).apply()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        dao.getTotalIncome().observe(viewLifecycleOwner) { income ->
            val incomeValue = income ?: 0.0
            dao.getTotalExpense().observe(viewLifecycleOwner) { expense ->
                val expenseValue = expense ?: 0.0
                val balance = incomeValue - expenseValue
            }
        }

        dao.getTotalIncome().observe(viewLifecycleOwner) { income ->
            val incomeValue = income ?: 0.0
            incomeTv.text = "Income\n₹$incomeValue"
            dao.getTotalExpense().observe(viewLifecycleOwner) { expense ->
                val expenseValue = expense ?: 0.0
                expenseTv.text = "Expense\n₹$expenseValue"
                val balance = incomeValue - expenseValue
                balanceTv.text = "₹$balance"

                val goal = prefs.getInt("goal", 0)
                val progressBar = view.findViewById<ProgressBar>(R.id.progress_goal)
                val goalText = view.findViewById<TextView>(R.id.tv_goal_status)

                val savings = balance
                goalText.text = "₹$savings / ₹$goal"

                if (goal > 0) {
                    val progress = ((savings / goal.toDouble()) * 100).toInt()
                    progressBar.progress = progress.coerceAtMost(100)
                }
            }
        }
    }
}