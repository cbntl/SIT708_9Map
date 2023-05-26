package com.example.lostfoundapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class ItemsDatabase extends SQLiteOpenHelper {

    public ItemsDatabase(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_POST_TABLE = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", Util.TABLE_NAME, Util.ITEM_ID, Util.ITEM_NAME, Util.PHONE_NUMBER, Util.DESCRIPTION, Util.TYPE, Util.DATE, Util.LOCATION, Util.LAT, Util.LON);

//        String CREATE_POST_TABLE = "CREATE TABLE items (id TEXT PRIMARY KEY, name TEXT, type TEXT, phone TEXT, description TEXT, date TEXT, lat TEXT, lon TEXT)";
        sqLiteDatabase.execSQL(CREATE_POST_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add the "lat" and "lon" columns to the "items" table
            db.execSQL("ALTER TABLE items ADD COLUMN lat REAL");
            db.execSQL("ALTER TABLE items ADD COLUMN lon REAL");
        }
    }

    public long addItem(Item post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.ITEM_NAME, post.getItemName());
        contentValues.put(Util.PHONE_NUMBER, post.getPhoneNumber());
        contentValues.put(Util.DESCRIPTION, post.getDescription());
        contentValues.put(Util.TYPE, post.getType());
        contentValues.put(Util.DATE, post.getDate());
        contentValues.put(Util.LOCATION, post.getLocation());
        contentValues.put(Util.LAT, post.getLat());
        contentValues.put(Util.LON, post.getLon());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public List<Item> getItems() {
        List<Item> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Util.ITEM_ID, Util.ITEM_NAME, Util.PHONE_NUMBER, Util.DESCRIPTION, Util.TYPE, Util.DATE, Util.LOCATION, Util.LAT, Util.LON};
        Cursor cursor = db.query(Util.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Util.ITEM_ID));
                Item post = getPostById(id);
                posts.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return posts;
    }



    public Item getPostById(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Util.ITEM_ID, Util.ITEM_NAME, Util.PHONE_NUMBER, Util.DESCRIPTION, Util.TYPE, Util.DATE, Util.LOCATION, Util.LAT, Util.LON};
        String selection = Util.ITEM_ID + "=?";
        String[] selectionArgs = {String.valueOf(postId)};
        Cursor cursor = db.query(Util.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Item post = null;
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(Util.ITEM_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Util.ITEM_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(Util.PHONE_NUMBER));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(Util.DESCRIPTION));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(Util.TYPE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(Util.DATE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(Util.LOCATION));
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(Util.LAT));
            double lon = cursor.getDouble(cursor.getColumnIndexOrThrow(Util.LON));
            post = new Item(id, name, phoneNumber, description, state, date, location, lat, lon);
        }

        cursor.close();
        return post;
    }


    public int deleteItem(int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Util.ITEM_ID + "=?";
        String[] selectionArgs = {String.valueOf(postId)};
        int count = db.delete(Util.TABLE_NAME, selection, selectionArgs);
        db.close();
        return count;
    }

}
