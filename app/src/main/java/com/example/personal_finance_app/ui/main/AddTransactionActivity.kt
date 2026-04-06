package com.example.personal_finance_app.ui.main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.personal_finance_app.R
import com.example.personal_finance_app.data.local.AppDatabase
import com.example.personal_finance_app.data.local.TransactionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val amountEt = findViewById<EditText>(R.id.et_amount)
        val categoryEt = findViewById<EditText>(R.id.et_category)
        val noteEt = findViewById<EditText>(R.id.et_note)
        val radioGroup = findViewById<RadioGroup>(R.id.rg_type)
        val saveBtn = findViewById<Button>(R.id.btn_save)

        val db = AppDatabase.getDatabase(this)
        val dao = db.transactionDao()

        saveBtn.setOnClickListener {

            val amountText = amountEt.text.toString()
            val category = categoryEt.text.toString()
            val note = noteEt.text.toString()

            // Validation
            if (amountText.isEmpty()) {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Select type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDouble()

            val type = if (radioGroup.checkedRadioButtonId == R.id.rb_income) {
                "income"
            } else {
                "expense"
            }

            // Save to DB
            CoroutineScope(Dispatchers.IO).launch {
                dao.insert(
                    TransactionEntity(
                        amount = amount,
                        type = type,
                        category = category,
                        note = note,
                        date = System.currentTimeMillis()
                    )
                )

                runOnUiThread {
                    Toast.makeText(this@AddTransactionActivity, "Saved!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}