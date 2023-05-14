package com.example.lostfoundapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createPost = findViewById(R.id.create_item);
        Button showAllPost = findViewById(R.id.show_all_items);

        createPost.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CreateItem.class)));

        showAllPost.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GetItems.class)));
    }
}