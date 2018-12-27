package com.example.kimtaeheon.p2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*StoreDetailActivity는 특정 store를 클릭할 시 해당 상점이 팔고 있는
 * 제품에 대해서 이 activity에 나타낸다.*/
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

        //commucnicationManager에서 store정보를 받아고 searchStroe를 이용하여
        //해당 상점에 팔고있는 물품들의 정보를 가져온다.
        communicationManager = CommunicationManager.getInstance();
        store = communicationManager.changeActivityStore(null);
        ArrayList<Product> products = communicationManager.searchStroe(store.name, null);

        name.setText(store.name);
        explan.setText(store.explanation);

        //recyclerView에 adpater와 decoration을 달아준다.
        adapter = new ListProdouctAdapter(this, R.layout.list_product, products, Product.OPT.PLUS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

        storeName= getIntent().getStringExtra("EXTRA_SESSION_ID");
    }

}
