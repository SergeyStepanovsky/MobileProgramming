package com.example.customadapterdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private UserListAdapter userListAdapter;
    private ListView userListView;
    private ArrayList<User> userList;
    private int currentSelectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        loadData();
    }

    private void setupUI() {
        userListView = findViewById(R.id.list);
        Button btnAddUser = findViewById(R.id.addButton);
        btnAddUser.setOnClickListener(v -> showAddUserDialog());

        findViewById(R.id.sortByName).setOnClickListener(v -> userListAdapter.sortUsersByName());
        findViewById(R.id.sortByPhone).setOnClickListener(v -> userListAdapter.sortUsersByPhoneNumber());
        findViewById(R.id.sortBySex).setOnClickListener(v -> userListAdapter.sortUsersBySex());


        userListView.setOnItemClickListener(this::onUserListItemClick);
    }

    private void loadData() {
        userList = fetchUsers();
        userListAdapter = new UserListAdapter(this, userList);
        userListView.setAdapter(userListAdapter);
    }

    private void onUserListItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSelectedIndex = position;
        userListAdapter.setHighlightedItemIndex(currentSelectedIndex);
    }

    private void showAddUserDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("Добавить пользователя");
        View dialogView = getLayoutInflater().inflate(R.layout.add_user_dialog, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setPositiveButton("Добавить", (dialog, which) -> addUser(dialogView));
        dialogBuilder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        AlertDialog addUserDialog = dialogBuilder.create();
        addUserDialog.show();
    }

    private void addUser(View dialogView) {
        EditText nameField = dialogView.findViewById(R.id.editTextName);
        EditText phoneField = dialogView.findViewById(R.id.editTextPhone);
        RadioGroup sexGroup = dialogView.findViewById(R.id.radioGroupSex);

        String userName = nameField.getText().toString();
        String userPhone = phoneField.getText().toString();
        Sex userSex = getSelectedSex(sexGroup);

        User newUser = new User(userName, userPhone, userSex);
        userListAdapter.addNewUser(newUser);
    }

    private Sex getSelectedSex(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        View selectedRadioButton = group.findViewById(selectedId);
        int index = group.indexOfChild(selectedRadioButton);
        return Sex.values()[index];
    }

    private ArrayList<User> fetchUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            String jsonStr = loadJSONFromAsset("users.json");
            JSONArray usersArray = new JSONArray(jsonStr);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObj = usersArray.getJSONObject(i);
                String name = userObj.getString("name");
                String phoneNumber = userObj.getString("phoneNumber");
                Sex sex = Sex.valueOf(userObj.getString("sex").toUpperCase());

                userList.add(new User(name, phoneNumber, sex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
