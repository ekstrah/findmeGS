package com.example.kimtaeheon.p2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */

public class ThreeFragment extends Fragment {
    CommunicationManager communicationManager;
    ListProdouctAdapter adapter;
    Product.OPT opt = null;
    ArrayList<Product> products;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView=(RecyclerView)inflater.inflate(R.layout.fragment_three, container, false);

        communicationManager = CommunicationManager.getInstance();
        if(opt == Product.OPT.MIUS){
            products = communicationManager.getFavorit_products();

            adapter = new ListProdouctAdapter(getActivity(), R.layout.list_product, products, opt);
            communicationManager.setFavoritProductsAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new MyItemDecoration());
        }else{
            ArrayList<Product> products = communicationManager.initProduct();

            adapter = new ListProdouctAdapter(getActivity(), R.layout.list_product, products, opt);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new MyItemDecoration());
        }


        return recyclerView;
    }


}
