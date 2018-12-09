package com.example.kimtaeheon.p2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProdouctDetailActivity extends AppCompatActivity {
    CommunicationManager communicationManager;
    ListStoreAdapter adapter;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView name, explan;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodouct_detail);
        recyclerView=(RecyclerView)findViewById(R.id.detail_product_recyclerview);
        imageView=(ImageView)findViewById(R.id.prodouct_detail_image);
        name=(TextView)findViewById(R.id.prodouct_detail_name);
        explan=(TextView)findViewById(R.id.prodouct_detail_explanation);

        communicationManager = CommunicationManager.getInstance();
        ArrayList<Store> stores = communicationManager.initStore();
        product = communicationManager.changeActivityProduct(null);

        name.setText(product.name);
        explan.setText(product.exlpan);

        adapter = new ListStoreAdapter(this, R.layout.list_store, stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());
    }
}
