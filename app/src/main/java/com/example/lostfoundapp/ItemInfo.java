package com.example.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemInfo extends AppCompatActivity {
    ItemsDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);

        TextView title = findViewById(R.id.titleTextView);
        TextView date = findViewById(R.id.itemTime);
        TextView description = findViewById(R.id.descriptionTextView);
        TextView location = findViewById(R.id.itemLocation);
        Button removePost = findViewById(R.id.removeItem);
        db = new ItemsDatabase(this);

        Intent intent = getIntent();
        String postId = intent.getStringExtra("post_id");
        Item post = db.getPostById(Integer.parseInt(postId));

        title.setText(post.getType() + " " + post.getItemName());
        date.setText(getTimeAgo(post.getDate()));
        description.setText(post.getDescription());
        location.setText(post.getLocation());


        removePost.setOnClickListener(view -> {
            String result;
            result = deleteCurrentItem(post.getItem_id());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            finish();
        });


    }


    private static String getTimeAgo(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("en-AU"));
        try {
            Date date = dateFormat.parse(dateString);
            long timeInMillis = date.getTime();

            long now = System.currentTimeMillis();
            CharSequence ago = DateUtils.getRelativeTimeSpanString(timeInMillis, now, DateUtils.MINUTE_IN_MILLIS);
            return ago.toString();
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage(), e);
        }
        return dateString;
    }

    private String deleteCurrentItem(int item_id) {
        long result = db.deleteItem(item_id);

        if (result > 0) return "This item has been successfully deleted!";
        return "Error found during deletion. Please try again later";
    }


}