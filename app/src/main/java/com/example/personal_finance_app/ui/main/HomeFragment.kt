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
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.charts.PieChart


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
        val pieChart = view.findViewById<PieChart>(R.id.pie_chart)

        val dao = AppDatabase.getDatabase(requireContext()).transactionDao()

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
                    val goalString = editText.text.toString()
                    val goal = goalString.toIntOrNull() ?: 0
                    prefs.edit().putInt("goal", goal).apply()
                    // Trigger a refresh of the UI if needed
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Use a single observation chain to update all UI elements at once
        dao.getTotalIncome().observe(viewLifecycleOwner) { income ->
            val incomeValue = income ?: 0.0
            incomeTv.text = "Income\n₹$incomeValue"

            dao.getTotalExpense().observe(viewLifecycleOwner) { expense ->
                val expenseValue = expense ?: 0.0
                expenseTv.text = "Expense\n₹$expenseValue"

                val balance = incomeValue - expenseValue
                balanceTv.text = "₹$balance"

                // Update Goal Progress
                val goal = prefs.getInt("goal", 0)
                goalText.text = "₹$balance / ₹$goal"

                if (goal > 0) {
                    val progress = ((balance / goal.toDouble()) * 100).toInt()
                    progressBar.progress = progress.coerceAtMost(100)
                } else {
                    progressBar.progress = 0
                }

                // Update Pie Chart
                updatePieChart(pieChart, incomeValue, expenseValue)
            }
        }
    }

    private fun updatePieChart(pieChart: PieChart, income: Double, expense: Double) {
        val entries = ArrayList<PieEntry>()

        if (income > 0) {
            entries.add(PieEntry(income.toFloat(), "Income"))
        }

        if (expense > 0) {
            entries.add(PieEntry(expense.toFloat(), "Expense"))
        }

        if (entries.isEmpty()) {
            pieChart.clear()
            return
        }

        val dataSet = PieDataSet(entries, "Finance Overview")
        dataSet.colors = listOf(
            android.graphics.Color.parseColor("#00B894"), // income
            android.graphics.Color.parseColor("#D63031")  // expense
        )
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(android.graphics.Color.WHITE)
        data.setValueFormatter(PercentFormatter(pieChart))

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(android.graphics.Color.BLACK)
        pieChart.centerText = "Spending"
        pieChart.setHoleColor(android.graphics.Color.WHITE)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}
