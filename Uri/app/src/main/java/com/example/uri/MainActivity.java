package com.example.uri;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private RadioGroup radioGroup;

    private int countSuccessInput = 0;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        radioGroup = findViewById(R.id.radioGroup);

    }

    public void onButtonClick(View view) {
        String text = inputText.getText().toString();
        selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {

            handleInput(text, true);

        } else {
            handleInput(text, false);
        }
    }

    private void handleInput(String text, boolean autoDetect) {
        boolean uncorrectInput = false;
        boolean uncorrectInputOrChoice = false;

        if (autoDetect || radioGroup.getCheckedRadioButtonId() == R.id.radioWeb) {

            Log.d("AUTODETECT", "autoDetect is: " + autoDetect);

            if (text.matches("^https?:\\/\\/.+")) {
                openWebPage(text);
                countSuccessInput += 1;
                uncorrectInput = false;
            }
            else if (autoDetect) {
                uncorrectInput = true;
            }
            else if (selectedId != -1) {
                showToast("Неверный ввод или выбор");
            }

        }
        if (autoDetect || radioGroup.getCheckedRadioButtonId() == R.id.radioGeo) {

            if (text.matches("^-?\\d+(\\.\\d+)?,\\s*-?\\d+(\\.\\d+)?$")) {
                openMap(text);
                countSuccessInput += 1;
                uncorrectInput = false;
            } else if (autoDetect) {
                uncorrectInput = true;
            } else if (selectedId != -1) {
                showToast("Неверный ввод или выбор");
            }
        }
        if (autoDetect || radioGroup.getCheckedRadioButtonId() == R.id.radioPhone) {
            if (text.matches("(\\+7|8)\\d{10}")) {
                showToast("Осуществляю звонок по телефону: " + text);
                countSuccessInput += 1;
                uncorrectInput = false;
            } else if (autoDetect) {
                uncorrectInput = true;
            } else if (selectedId != -1) {
                showToast("Неверный ввод или выбор");
            }
        }
        if(uncorrectInput == true && countSuccessInput == 0){
            showToast("Неверный ввод");
        }

        uncorrectInput = false;
        countSuccessInput =0;

    }


    private void openWebPage(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            showToast("Ошибка при попытке открыть веб-страницу: " + e.getMessage());
        }
    }


    private void openMap(String geoCoordinates) {
        // Проверка и форматирование входных данных
        if (!isValidGeoCoordinates(geoCoordinates)) {
            showToast("Неверный формат географических координат");
            return;
        }

        // Создание URI для карты
        String uri = String.format("geo:%s?z=10", geoCoordinates);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(mapIntent);
    }


    // Метод для валидации географических координат
    private boolean isValidGeoCoordinates(String geoCoordinates) {
        // Регулярное выражение для проверки координат (широта, долгота)
        String regex = "^-?\\d+(\\.\\d+)?,\\s*-?\\d+(\\.\\d+)?$";
        return geoCoordinates.matches(regex);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
