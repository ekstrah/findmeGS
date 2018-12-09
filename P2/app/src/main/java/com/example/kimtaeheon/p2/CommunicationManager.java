package com.example.kimtaeheon.p2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class CommunicationManager {
    private static Retrofit retrofit;
    private static RetrofitExService retrofitExService;


    private static volatile CommunicationManager communicationManager = null;

    private CommunicationManager() {}

    public static CommunicationManager getInstance(){
        if(communicationManager==null){
            synchronized (CommunicationManager.class){
                if(communicationManager==null){
                    communicationManager= new CommunicationManager();
                    retrofit = new Retrofit.Builder().baseUrl(RetrofitExService.URL).addConverterFactory(GsonConverterFactory.create()).build();
                    retrofitExService = retrofit.create(RetrofitExService.class);
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
        products.add(new Product("sdfa", "adsfdsafsadfsad"));
        products.add(new Product("asdfdsaf", "asdfdasf"));
        products.add(new Product("sadfsfad", "fasdfadsfsdafasd."));
        products.add(new Product("sdfa", "adsfdsafsadfsad"));
        products.add(new Product("asdfdsaf", "asdfdasf"));
        products.add(new Product("sadfsfad", "fasdfadsfsdafasd."));
        products.add(new Product("sdfa", "adsfdsafsadfsad"));
        products.add(new Product("asdfdsaf", "asdfdasf"));
        products.add(new Product("sadfsfad", "fasdfadsfsdafasd."));
        products.add(new Product("sdfa", "adsfdsafsadfsad"));
        products.add(new Product("asdfdsaf", "asdfdasf"));
        products.add(new Product("sadfsfad", "fasdfadsfsdafasd."));

        return products;
    }

    //ArrayList를 바꾸고 adapter.changeItem을 호출!
    public ArrayList<Product> searchProduct(String productName, ListStoreAdapter adapter){
        final ArrayList<Product> products = new ArrayList();
        Log.d("haha : ",productName);
        retrofitExService.getItem().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                {
                    List <Product> producted = response.body();
                    //if(products!=null)
                    //{
                        for(int i=0;i<producted.size();i++)
                        Log.e("abd : "+i,producted.get(i).getProductName());
                    //}

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        return products;
    }

    public ArrayList<Product> searchStroe(String storeName, RecyclerView.Adapter<ListStoreHolder> adapter){
        final ArrayList<Product> products = new ArrayList<>();
        retrofitExService.getLocation(storeName).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                {
                    products.addAll(response.body());
                    /*if(products!=null)
                    {
                        Log.d("name : ",products.get(0).name);
                    }*/
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        return products;
    }
    public void OccurMarkingTouch(Marker marker)
    {

    }
}
