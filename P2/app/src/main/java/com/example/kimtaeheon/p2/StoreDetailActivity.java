package com.example.kimtaeheon.p2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class StoreDetailActivity extends AppCompatActivity {
    CommunicationManager communicationManager;
    ListStoreAdapter adapter;
    String storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.detail_store_recyclerview);

        communicationManager = CommunicationManager.getInstance();
        ArrayList<Store> stores = communicationManager.initStore();

        adapter = new ListStoreAdapter(this, R.layout.list_store, stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

        storeName= getIntent().getStringExtra("EXTRA_SESSION_ID");
    }

}
