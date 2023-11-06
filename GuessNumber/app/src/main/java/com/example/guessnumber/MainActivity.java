package com.example.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMin;
    private EditText editTextMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMin = findViewById(R.id.editTextMin);
        editTextMax = findViewById(R.id.editTextMax);

        Button startGameButton = findViewById(R.id.startGameButton); // Замените на ID кнопки в вашем XML.
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    public void startGame() {
        int minNumber = Integer.parseInt(editTextMin.getText().toString().isEmpty() ? "1" : editTextMin.getText().toString());
        int maxNumber = Integer.parseInt(editTextMax.getText().toString().isEmpty() ? "100" : editTextMax.getText().toString());

        Intent intent = new Intent(this, GuessActivity.class);
        intent.putExtra("min", minNumber);
        intent.putExtra("max", maxNumber);
        startActivity(intent);
    }
}
