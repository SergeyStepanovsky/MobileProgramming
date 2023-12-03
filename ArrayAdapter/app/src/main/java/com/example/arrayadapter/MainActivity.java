package com.example.arrayadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> userDataList;
    private ArrayAdapter<String> userDataListAdapter;
    private EditText nameEditText, surnameEditText;
    private int highlightedItemIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        ListView userListView = findViewById(R.id.userListView);
        userDataList = new ArrayList<>();
        userDataListAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.userTextView, userDataList);
        userListView.setAdapter(userDataListAdapter);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);

        Button addRandomUserButton = findViewById(R.id.addRandomUserButton);
        addRandomUserButton.setOnClickListener(view -> addRandomUser());

        Button addUserButton = findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(view -> addUser());

        userListView.setOnItemClickListener((parent, view, position, id) -> {
            if (highlightedItemIndex >= 0) {
                parent.getChildAt(highlightedItemIndex).setBackgroundColor(Color.TRANSPARENT);
            }
            view.setBackgroundColor(Color.LTGRAY);
            highlightedItemIndex = position;
        });
    }

    private void addRandomUser() {
        String[] givenNames = getResources().getStringArray(R.array.given_names_array);
        String[] familyNames = getResources().getStringArray(R.array.surnames);
        Random randomGenerator = new Random();

        String randomGivenName = givenNames[randomGenerator.nextInt(givenNames.length)];
        String randomFamilyName = familyNames[randomGenerator.nextInt(familyNames.length)];
        String fullName = randomGivenName + " " + randomFamilyName;
        userDataList.add(fullName);
        userDataListAdapter.notifyDataSetChanged();
    }

    private void addUser() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        if (!name.isEmpty() && !surname.isEmpty()) {
            String fullName = name + " " + surname;
            userDataList.add(fullName);
            userDataListAdapter.notifyDataSetChanged();
        }
    }
}
