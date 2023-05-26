package com.example.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;

public class GetItems extends AppCompatActivity implements ItemRecyclerView.OnItemClickListener {
    ItemsDatabase db;
    private List<Item> itemList;
    private RecyclerView recyclerView;
    private ItemRecyclerView itemRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_items);
        db = new ItemsDatabase(this);
        this.deleteDatabase(String.valueOf(db));

        recyclerView = findViewById(R.id.view_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        itemList = new ArrayList<>();
        itemRecyclerView = new ItemRecyclerView(itemList, this::onItemClick);
        recyclerView.setAdapter(itemRecyclerView);
        itemList.addAll((Collection<? extends Item>) db.getItems());
        itemRecyclerView.notifyDataSetChanged();


    }


    @Override
    public void onItemClick(Item post) {
        Intent intent = new Intent(this, ItemInfo.class);
        intent.putExtra("post_id", String.valueOf(post.getItem_id()));
        startActivity(intent);
    }



}
