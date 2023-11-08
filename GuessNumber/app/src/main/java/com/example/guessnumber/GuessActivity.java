package com.example.guessnumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GuessActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private Button buttonHigher, buttonLower, buttonCorrect;
    private int min;
    private int max;
    private int guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonHigher = findViewById(R.id.buttonHigher);
        buttonLower = findViewById(R.id.buttonLower);
        buttonCorrect = findViewById(R.id.buttonCorrect);

        Intent intent = getIntent();
        min = intent.getIntExtra("min", 1);
        max = intent.getIntExtra("max", 100);
        guess = (min + max) / 2;

        askNumber();

        buttonHigher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHigher();
            }
        });

        buttonLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLower();
            }
        });

        buttonCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCorrect();
            }
        });
    }

    private void askNumber() {
        textViewQuestion.setText(String.format("Is your number %d?", guess));
    }

    private void onHigher() {
        min = guess + 1;
        guess = (min + max + 1) / 2;
        if (min >= max) {
            guess = max;
            onCorrect();
        } else {
            askNumber();
        }
    }

    private void onLower() {
        max = guess - 1;
        guess = (min + max) / 2;
        if (min >= max) {
            guess = max;
            onCorrect();
        } else {
            askNumber();
        }
    }

    private void onCorrect() {
        textViewQuestion.setText(String.format("Your number is %d! The game is over!", guess));
        buttonHigher.setEnabled(false);
        buttonLower.setEnabled(false);
        buttonCorrect.setEnabled(false);
        // Добавьте здесь код для завершения игры или начала новой.
    }
}

