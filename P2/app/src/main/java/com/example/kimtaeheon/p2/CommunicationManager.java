package com.example.kimtaeheon.p2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.Marker;

import java.io.UnsupportedEncodingException;
import java.net.PortUnreachableException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class CommunicationManager {
    private Product product;
    private Store store;
    private ArrayList<Product> favorit_products = new ArrayList<>();
    private ListProdouctAdapter adapter;
    private  ArrayList<Product> products;

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
        for(int i = 0;i<10;i++)
        {
            stores.add(new Store(products.get(i).getLocation(), products.get(i).getSale()));
        }
        /*
        stores.add(new Store("GS", "Gs very very good~"));
        stores.add(new Store("CU","CU dosikrock is delicious"));
        stores.add(new Store("Sibal", "Sibal asdfkljaskj"));
        stores.add(new Store("GS", "Gs very very good~"));
        stores.add(new Store("CU","CU dosikrock is delicious"));
        stores.add(new Store("Sibal", "Sibal asdfkljaskj"));
        stores.add(new Store("GS", "Gs very very good~"));
        stores.add(new Store("CU","CU dosikrock is delicious"));
        stores.add(new Store("Sibal", "Sibal asdfkljaskj"));
        */

        return stores;
    }

    public ArrayList<Product> initProduct(){
        ArrayList<Product> products = new ArrayList<>();
        for(int i = 0;i<10;i++)
        {
            products.add(new Product(this.products.get(i).getProductName(), this.products.get(i).getPrice()));
        }
        /*products.add(new Product("sdfa", "adsfdsafsadfsad"));
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
        products.add(new Product("sadfsfad", "fasdfadsfsdafasd."));*/

        return products;
    }

    //ArrayList를 바꾸고 adapter.changeItem을 호출!
    public ArrayList<Store> searchProduct(String productName, ListStoreAdapter adapter){
        final ArrayList<Store> sp = new ArrayList();
        for(int i = 0;i<products.size();i++)
        {
            if(productName.equals(products.get(i).getProductName()))
            {
                sp.add(new Store(products.get(i).getLocation(),products.get(i).getPrice()));
            }
        }
        Collections.sort(sp, new AscendingInteger());
        return sp;
    }

    public ArrayList<Product> searchStroe(String storeName, RecyclerView.Adapter<ListStoreHolder> adapter){
        final ArrayList<Product> pr = new ArrayList<>();
        for(int i = 0;i<products.size();i++)
        {
            if(storeName.equals(products.get(i).getLocation()))
            {
                pr.add(new Product(products.get(i).getProductName(),products.get(i).getPrice()));
            }
        }
        return pr;
    }
    public void OccurMarkingTouch(ArrayList<Marker> marker)
    {

    }

    public void setProducts(ArrayList products)
    {
        this.products=products;
    }

    public void setFavoritProductsAdapter(ListProdouctAdapter adapter){
        this.adapter = adapter;
    }

    public void addFavoirtProduct(Product product){
        this.favorit_products.add(product);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
    }

    public ArrayList<Product> getFavorit_products() {
        return favorit_products;
    }

    public Store changeActivityStore(Store store){
        if(store != null){
            this.store = store;
        }
        return this.store;
    }
    public Product changeActivityProduct(Product product){
        if(product != null){
            this.product = product;
        }
        return this.product;
    }
    public void selectedTab(int p){

    }
}

class AscendingInteger implements Comparator<Store> {

    @Override
    public int compare(Store o1, Store o2) {
        int o1Data, o2Data;

        o1Data = Integer.parseInt(o1.explanation);
        o2Data = Integer.parseInt(o2.explanation);

        return Integer.compare(o1Data, o2Data);
    }
}