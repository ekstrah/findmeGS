package com.example.kimtaeheon.p2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*ProductDetailactivity는 특정 product를 클릭할 시 해당 제품을 팔고 있는
* 상점들에 대해서 이 activity에서 나타낸다.*/
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

        //commucnicationManager에서 product정보를 받아고 searchProduct를 이용하여
        //해당 제품을 팔고있는 상점들의 정보를 가져온다.
        communicationManager = CommunicationManager.getInstance();
        product = communicationManager.changeActivityProduct(null);
        ArrayList<Store> stores = communicationManager.searchProduct(product.name, null);

        name.setText(product.name);
        explan.setText(product.exlpan);

        //recyclerView에 adpater와 decoration을 달아준다.
        adapter = new ListStoreAdapter(this, R.layout.list_store, stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());
    }
}
