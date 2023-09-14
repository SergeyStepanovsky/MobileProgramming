package com.example.additionoftwonumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        view.setBackgroundColor(Color.BLACK);

        EditText et1 = findViewById(R.id.numA);
        EditText et2 = findViewById(R.id.numB);
        String stA = et1.getText().toString();
        String stB = et2.getText().toString();

        // Проверка на пустые значения
        if (stA.isEmpty() || stB.isEmpty()) {
            Toast.makeText(this, "Оба поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            return; // выход из метода
        }

        int sum = Integer.parseInt(stA) + Integer.parseInt(stB);
        TextView tvSum = findViewById(R.id.sum);
        tvSum.setText(Integer.toString(sum));
    }
}