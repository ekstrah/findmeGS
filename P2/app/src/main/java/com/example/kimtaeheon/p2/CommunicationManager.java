package com.example.kimtaeheon.p2;

import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CommunicationManager {

    private static volatile CommunicationManager communicationManager = null;

    private CommunicationManager() {}

    public static CommunicationManager getInstance(){
        if(communicationManager==null){
            synchronized (CommunicationManager.class){
                if(communicationManager==null){
                    communicationManager= new CommunicationManager();
                }
            }
        }
        return communicationManager;
    }

    public ArrayList<Store> initStore(){
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(new Store("GS", "Gs very very good~"));
        stores.add(new Store("CU","CU dosikrock is delicious"));
        stores.add(new Store("Sibal", "Sibal asdfkljaskj"));
        stores.add(new Store("GS", "Gs very very good~"));
        stores.add(new Store("CU","CU dosikrock is delicious"));
        stores.add(new Store("Sibal", "Sibal asdfkljaskj"));
        stores.add(new Store("GS", "Gs very very good~"));
        stores.add(new Store("CU","CU dosikrock is delicious"));
        stores.add(new Store("Sibal", "Sibal asdfkljaskj"));

        return stores;
    }

    public ArrayList<Product> initProduct(){
        ArrayList<Product> products = new ArrayList<>();

        return products;
    }

    //ArrayList를 바꾸고 adapter.changeItem을 호출!
    public ArrayList<Store> searchProduct(String productName, ListStoreAdapter adapter){
        ArrayList<Store> stores = new ArrayList<>();


        return stores;
    }

    public ArrayList<Product> searchStroe(String storeName, RecyclerView.Adapter<ListStoreHolder> adapter){
        ArrayList<Product> products = new ArrayList<>();

        return products;
    }
}
