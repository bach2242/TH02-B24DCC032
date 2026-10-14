package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvDetailName, tvDetailAmount, tvDetailCategory, tvDetailDate, tvDetailNote, tvBackDetail;
    private Button btnBack, btnDelete;
    private int expensePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailAmount = findViewById(R.id.tvDetailAmount);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        tvBackDetail = findViewById(R.id.tvBackDetail);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        if (intent != null) {
            Expense expense = (Expense) intent.getSerializableExtra("expense_data");
            expensePosition = intent.getIntExtra("expense_position", -1);

            if (expense != null) {
                tvDetailName.setText(expense.getName());
                tvDetailAmount.setText(Expense.formatCurrency(expense.getAmount()));
                tvDetailCategory.setText(expense.getCategory());
                tvDetailDate.setText(expense.getDate());

                String note = expense.getNote();
                tvDetailNote.setText((note == null || note.isEmpty()) ? "Không có ghi chú" : note);
            }
        }

        tvBackDetail.setOnClickListener(v -> finish());
        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("delete_position", expensePosition);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(ExpenseDetailActivity.this, "Đã xóa khoản chi", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}