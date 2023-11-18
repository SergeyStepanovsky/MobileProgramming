package com.example.uri2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        radioGroup = findViewById(R.id.radioGroup);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    // Автоматическое определение действия
                    performActionBasedOnInput(input);
                } else {
                    // Выбор действия в зависимости от выбранной радиокнопки
                    performSelectedAction(input, selectedId);
                }
            }
        });
    }

    private void performActionBasedOnInput(String input) {
        if (isWebAddress(input)) {
            openWebPage(input);
        } else if (isGeoPoint(input)) {
            openMap(input);
        } else if (isPhoneNumber(input)) {
            simulateCall(input);
        } else {
            showError("Неверный ввод");
        }
    }

    private void performSelectedAction(String input, int selectedId) {
        switch (selectedId) {
            case R.id.radioWeb:
                if (isWebAddress(input)) openWebPage(input);
                else showError("Неверный формат веб-адреса");
                break;
            case R.id.radioGeo:
                if (isGeoPoint(input)) openMap(input);
                else showError("Неверный формат геоточки");
                break;
            case R.id.radioPhone:
                if (isPhoneNumber(input)) simulateCall(input);
                else showError("Неверный формат номера телефона");
                break;
            default:
                showError("Выберите один из вариантов");
                break;
        }
    }

    private boolean isWebAddress(String input) {
        // Реализуйте проверку URL
        return input.startsWith("http://") || input.startsWith("https://");
    }

    private boolean isGeoPoint(String input) {
        // Реализуйте проверку координат
        return input.matches("^[\\-]?[\\d]+\\.[\\d]+,[\\-]?[\\d]+\\.[\\d]+$");
    }

    private boolean isPhoneNumber(String input) {
        // Реализуйте проверку номера телефона
        return input.matches("^([8|\\+7]\\d{10})$");
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void openMap(String geoPoint) {
        Uri gmmIntentUri = Uri.parse("geo:" + geoPoint);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void simulateCall(String phoneNumber) {
        Toast.makeText(this, "Осуществляю звонок по телефону " + phoneNumber, Toast.LENGTH_LONG).show();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
