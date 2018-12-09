package com.example.kimtaeheon.p2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StoreDetailActivity extends AppCompatActivity {
    CommunicationManager communicationManager;
    ListProdouctAdapter adapter;
    String storeName;
    ImageView imageView1, imageView2;
    TextView name, explan;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.detail_store_recyclerview);
        imageView1=(ImageView)findViewById(R.id.store_detail_image1);
        imageView2=(ImageView)findViewById(R.id.store_detail_image2);
        name=(TextView)findViewById(R.id.store_detail_name);
        explan=(TextView)findViewById(R.id.store_detail_explanation);

        communicationManager = CommunicationManager.getInstance();
        ArrayList<Product> products = communicationManager.initProduct();
        store = communicationManager.changeActivityStore(null);

        name.setText(store.name);
        explan.setText(store.explanation);

        adapter = new ListProdouctAdapter(this, R.layout.list_product, products, Product.OPT.PLUS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

        storeName= getIntent().getStringExtra("EXTRA_SESSION_ID");
    }

}
