package com.example.uri;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        radioGroup = findViewById(R.id.radioGroup);
    }

    public void onButtonClick(View view) {
        String text = inputText.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {

            handleInput(text, true);
        } else {

            handleInput(text, false);
        }
    }

    private void handleInput(String text, boolean autoDetect) {
        if (autoDetect || radioGroup.getCheckedRadioButtonId() == R.id.radioWeb) {
            if (text.matches("^https?:\\/\\/.+")) {

                openWebPage(text);
            } else if (autoDetect) {
                showToast("Неверный ввод");
            } else {
                showToast("Неверный ввод или выбор");
            }

        } else if (autoDetect || radioGroup.getCheckedRadioButtonId() == R.id.radioGeo) {
            if (text.matches("^-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?$")) {
                // Open map
                openMap(text);
            } else if (autoDetect) {
                showToast("Неверный ввод");
            } else {
                showToast("Неверный ввод или выбор");
            }
        } else if (autoDetect || radioGroup.getCheckedRadioButtonId() == R.id.radioPhone) {
            if (text.matches("(\\+7|8)\\d{10}|(\\+7|8)\\s?\\d{3}\\s?\\d{3}\\s?\\d{4}")) {
                showToast("Осуществляю звонок по телефону: " + text);
            } else if (autoDetect) {
                showToast("Неверный ввод");
            } else {
                showToast("Неверный ввод или выбор");
            }
        }
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
        String uri = "geo:" + geoCoordinates;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showToast("Нет приложения для отображения карт");
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
