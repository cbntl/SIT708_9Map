package com.example.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Locale;

public class CreateItem extends AppCompatActivity {
    ItemsDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_item);

        EditText itemName = findViewById(R.id.et_name);
        EditText contactNumber = findViewById(R.id.et_phone);
        EditText itemDescription = findViewById(R.id.multi_tv_description);
        EditText itemDate = findViewById(R.id.et_date);
        EditText itemLocation = findViewById(R.id.et_location);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        Button createItem = findViewById(R.id.create_item);
        db = new ItemsDatabase(this);

        itemDate.setOnClickListener(v -> {
            final Calendar calendr = Calendar.getInstance();
            int mYear = calendr.get(Calendar.YEAR);
            int mMonth = calendr.get(Calendar.MONTH);
            int mDay = calendr.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(CreateItem.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.forLanguageTag("en-AU"), "%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year);
                        itemDate.setText(selectedDate);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        createItem.setOnClickListener(view -> {
            String name = itemName.getText().toString();
            String phone = contactNumber.getText().toString();
            String description = itemDescription.getText().toString();
            String state = getState(radioGroup);
            String date = itemDate.getText().toString();
            String location = itemLocation.getText().toString();
            String result = "";
            if (name.equals(""))
                itemName.setError("Please enter name");
            else if (phone.equals(""))
                contactNumber.setError("Please enter phone number");
            else if (description.equals(""))
                itemDescription.setError("Please enter description");
            else if (date.equals(""))
                itemDate.setError("Please enter date");
            else if (location.equals(""))
                itemLocation.setError("Please enter location");
            else
                result = createPost(name, phone, description, state, date, location);
            Toast.makeText(CreateItem.this, result, Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private String getState(RadioGroup radioGroup) {
        String selectedState;
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.radio_lost)
            selectedState = "Lost";
        else
            selectedState = "Found";
        return selectedState;
    }

    private String createPost(String name, String phone, String description, String state, String date, String location) {
        long result = db.addItem(new Item(name, phone, description, state, date, location));

        if (result > 0) return "The item has been added to the list!";
        return "Error found in saving the item!";
    }

}