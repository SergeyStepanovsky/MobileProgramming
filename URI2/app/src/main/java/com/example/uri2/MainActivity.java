package com.example.uri2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private RadioGroup radioGroup;

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
        processInput(text, selectedId == -1);
    }

    private void processInput(String text, boolean autoDetect) {
        boolean validInput = false;

        validInput = tryProcessWebInput(text, autoDetect || selectedId == R.id.radioWeb) ||
                tryProcessGeoInput(text, autoDetect || selectedId == R.id.radioGeo) ||
                tryProcessPhoneInput(text, autoDetect || selectedId == R.id.radioPhone);

        if (!validInput) {
            if (autoDetect) {
                showToast("Неверный ввод");
            } else {
                showToast("Неверный выбор или ввод");
            }
        }
    }

    private boolean tryProcessWebInput(String text, boolean shouldProcess) {
        if (shouldProcess && text.matches("^https?:\\/\\/.+")) {
            openWebPage(text);
            return true;
        }
        return false;
    }

    private boolean tryProcessGeoInput(String text, boolean shouldProcess) {
        if (shouldProcess && text.matches("^-?\\d+(\\.\\d+)?,\\s*-?\\d+(\\.\\d+)?$")) {
            openMap(text);
            return true;
        }
        return false;
    }

    private boolean tryProcessPhoneInput(String text, boolean shouldProcess) {
        if (shouldProcess && text.matches("(\\+7|8)\\d{10}")) {
            showToast("Осуществляю звонок по телефону: " + text);
            return true;
        }
        return false;
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
        if (!isValidGeoCoordinates(geoCoordinates)) {
            showToast("Неверный формат географических координат");
            return;
        }

        String uri = String.format("geo:%s?z=10", geoCoordinates);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(mapIntent);
    }

    private boolean isValidGeoCoordinates(String geoCoordinates) {
        String regex = "^-?\\d+(\\.\\d+)?,\\s*-?\\d+(\\.\\d+)?$";
        return geoCoordinates.matches(regex);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
