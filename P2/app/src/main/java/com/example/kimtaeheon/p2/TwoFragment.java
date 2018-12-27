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
import android.widget.ListView;

import java.util.ArrayList;

/*ThreeFragment는 Store를 recyclerview로 보여주는 fragment이다.
 * Arraylist<Stroe>를 매개변수로 받아서, reyclerview에 adpater를 연결한다.*/
public class TwoFragment extends Fragment{
    CommunicationManager communicationManager;
    ListStoreAdapter adapter;
    ArrayList<Store> stores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView=(RecyclerView)inflater.inflate(R.layout.fragment_two, container, false);

        communicationManager = CommunicationManager.getInstance();
        stores = communicationManager.initStore();

        adapter = new ListStoreAdapter(getActivity(), R.layout.list_store, stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

        return recyclerView;
    }



}
