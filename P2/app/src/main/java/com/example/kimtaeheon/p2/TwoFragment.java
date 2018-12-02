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

/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */

public class TwoFragment extends Fragment{
    CommunicationManager communicationManager;
    ListStoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView=(RecyclerView)inflater.inflate(R.layout.fragment_two, container, false);

        communicationManager = CommunicationManager.getInstance();
        ArrayList<Store> stores = communicationManager.initStore();

        adapter = new ListStoreAdapter(getActivity(), R.layout.list_store, stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

        return recyclerView;
    }

}
