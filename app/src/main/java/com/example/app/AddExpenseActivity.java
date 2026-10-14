package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtExpenseName, edtExpenseAmount, edtExpenseNote;
    private Spinner spCategory;
    private DatePicker datePicker;
    private Button btnSaveExpense;
    private TextView tvBackAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtExpenseName = findViewById(R.id.edtExpenseName);
        edtExpenseAmount = findViewById(R.id.edtExpenseAmount);
        edtExpenseNote = findViewById(R.id.edtExpenseNote);
        spCategory = findViewById(R.id.spCategory);
        datePicker = findViewById(R.id.datePicker);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);
        tvBackAdd = findViewById(R.id.tvBackAdd);

        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        tvBackAdd.setOnClickListener(v -> finish());
        btnSaveExpense.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        String name = edtExpenseName.getText().toString().trim();
        String amountStr = edtExpenseAmount.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();
        String note = edtExpenseNote.getText().toString().trim();

        if (name.isEmpty()) {
            edtExpenseName.setError("Vui lòng nhập tên khoản chi");
            edtExpenseName.requestFocus();
            Toast.makeText(this, "Tên khoản chi không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amountStr.isEmpty()) {
            edtExpenseAmount.setError("Vui lòng nhập số tiền");
            edtExpenseAmount.requestFocus();
            Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        long amount;
        try {
            amount = Long.parseLong(amountStr);
            if (amount <= 0) {
                edtExpenseAmount.setError("Số tiền phải lớn hơn 0");
                edtExpenseAmount.requestFocus();
                Toast.makeText(this, "Số tiền phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            edtExpenseAmount.setError("Số tiền không hợp lệ");
            edtExpenseAmount.requestFocus();
            return;
        }

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);

        Expense newExpense = new Expense(name, amount, category, date, note);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_expense", newExpense);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Đã lưu khoản chi thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}