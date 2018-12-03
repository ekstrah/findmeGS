package com.example.kimtaeheon.p2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ProdouctDetailActivity extends AppCompatActivity {
    CommunicationManager communicationManager;
    ListProdouctAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodouct_detail);
        recyclerView=(RecyclerView)findViewById(R.id.detail_product_recyclerview);

        communicationManager = CommunicationManager.getInstance();
        ArrayList<Product> products = communicationManager.initProduct();

        adapter = new ListProdouctAdapter(this, R.layout.list_product, products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

    }
}
