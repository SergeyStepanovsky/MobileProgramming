package com.example.guessnumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GuessActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private int min;
    private int max;
    private int guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        textViewQuestion = findViewById(R.id.textViewQuestion);

        Intent intent = getIntent();
        min = intent.getIntExtra("min", 1);
        max = intent.getIntExtra("max", 100);
        guess = (min + max) / 2;

        askNumber();
    }

    private void askNumber() {
        textViewQuestion.setText(String.format("Your number is %d?", guess));
    }

    public void onHigher(View view) {
        min = guess + 1;
        guess = (min + max) / 2;
        askNumber();
    }

    public void onLower(View view) {
        max = guess - 1;
        guess = (min + max) / 2;
        askNumber();
    }
}
