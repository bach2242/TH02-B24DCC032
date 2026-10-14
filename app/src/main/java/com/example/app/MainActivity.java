package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private ListView lvExpenses;
    private Button btnAddExpense;

    private ArrayList<Expense> expenseList;
    private ExpenseAdapter adapter;

    private final ActivityResultLauncher<Intent> addExpenseLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Expense newExpense = (Expense) result.getData().getSerializableExtra("new_expense");
                    if (newExpense != null) {
                        expenseList.add(newExpense);
                        adapter.notifyDataSetChanged();
                        updateTotalAmount();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> detailExpenseLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int deletePosition = result.getData().getIntExtra("delete_position", -1);
                    if (deletePosition >= 0 && deletePosition < expenseList.size()) {
                        expenseList.remove(deletePosition);
                        adapter.notifyDataSetChanged();
                        updateTotalAmount();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        lvExpenses = findViewById(R.id.lvExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        initSampleData();

        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);
        updateTotalAmount();

        lvExpenses.setOnItemClickListener((parent, view, position, id) -> {
            Expense selectedExpense = expenseList.get(position);
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("expense_data", selectedExpense);
            intent.putExtra("expense_position", position);
            detailExpenseLauncher.launch(intent);
        });

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            addExpenseLauncher.launch(intent);
        });
    }

    private void initSampleData() {
        expenseList = new ArrayList<>();
        expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", "12/10/2026", "Bánh mì và sữa"));
        expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", "12/10/2026", "Đổ xăng xe máy"));
        expenseList.add(new Expense("Mua sách", 120000, "Học tập", "13/10/2026", "Sách Lập trình Android"));
    }

    private void updateTotalAmount() {
        long total = 0;
        for (Expense item : expenseList) {
            total += item.getAmount();
        }
        tvTotalAmount.setText(Expense.formatCurrency(total));
    }
}