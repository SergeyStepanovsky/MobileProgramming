package com.example.aviasales;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerDepartureCity;
    private Spinner spinnerArrivalCity;
    private EditText editTextDepartureDate;
    private EditText editTextReturnDate;
    private EditText editTextAdults;
    private EditText editTextChildren;
    private EditText editTextInfants;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDepartureCity = findViewById(R.id.spinner_departure_city);
        spinnerArrivalCity = findViewById(R.id.spinner_arrival_city);
        editTextDepartureDate = findViewById(R.id.edittext_departure_date);
        editTextReturnDate = findViewById(R.id.edittext_return_date);
        editTextAdults = findViewById(R.id.edittext_adults);
        editTextChildren = findViewById(R.id.edittext_children);
        editTextInfants = findViewById(R.id.edittext_infants);


        setDatePicker(editTextDepartureDate);
        setDatePicker(editTextReturnDate);
    }

    private void setDatePicker(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText, calendar);
            }
        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText editText, Calendar calendar) {
        String myFormat = "dd/MM/yyyy"; // В качестве примера, вы можете использовать любой формат даты
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(calendar.getTime()));
    }

}
