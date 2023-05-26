package com.example.lostfoundapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.widget.Button;

import java.io.IOException;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateItem extends AppCompatActivity {
    ItemsDatabase db;
    private Double lat, lon;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog datePickerDialog;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_item);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        EditText itemName = findViewById(R.id.et_name);
        EditText contactNumber = findViewById(R.id.et_phone);
        EditText itemDescription = findViewById(R.id.multi_tv_description);
        EditText itemDate = findViewById(R.id.et_date);
//        EditText editTextLocation = findViewById(R.id.editTextLocation);
        EditText itemLocation = findViewById(R.id.et_location);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        Button createItem = findViewById(R.id.create_item);
        Button getCurrLocationButton = findViewById(R.id.getCurrLocationButton);
        db = new ItemsDatabase(this);

//        itemDate.setOnClickListener(v -> {
//            final Calendar calendr = Calendar.getInstance();
//            int mYear = calendr.get(Calendar.YEAR);
//            int mMonth = calendr.get(Calendar.MONTH);
//            int mDay = calendr.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(CreateItem.this,
//                    (view, year, monthOfYear, dayOfMonth) -> {
//                        String selectedDate = String.format(Locale.forLanguageTag("en-AU"), "%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year);
//                        itemDate.setText(selectedDate);
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//        });

        // Initialize the date picker dialog
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            String monthString;
            if (month < 10) {
                monthString = "0" + month;
            } else {
                monthString = String.valueOf(month);
            }
            String dateString = dayOfMonth + "/" + monthString + "/" + year;
           itemDate.setText(dateString);
        };

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        itemDate.setOnFocusChangeListener((v, event) -> {
            if (event) {
                datePickerDialog.show();
                itemDate.clearFocus();
            }
        });
        getLocationPermission();
        getCurrLocationButton.setOnClickListener(v -> {
            if (locationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            if (location != null) {
                                // Use the location coordinates to retrieve place information
                                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                                List<Address> addresses;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    if (!addresses.isEmpty()) {
                                        Address address = addresses.get(0);
                                        String currentLocation = address.getAddressLine(0);

                                        itemLocation.setText(currentLocation);
                                        lat = location.getLatitude();
                                        lon = location.getLongitude();
                                        System.out.println("Latitude: " + lat + " Longitude: " + lon);
                                    } else {
                                        Toast.makeText(this, "Error retrieving current location", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {
                System.out.println("Location permission not granted");
            }

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
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private String createPost(String name, String phone, String description, String state, String date, String location) {
        long result = db.addItem(new Item(name, phone, description, state, date, location, lat, lon));

        if (result > 0) return "The item has been added to the list!";
        return "Error found in saving the item!";
    }


}