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

/*CommunicationManger은 싱글턴 패턴으로 만든 Class입니다.
* 이 Class는 2가지 경우에서 사용되는데 첫번 째는 Activity간에 통신이다.
* 이 Program은 즐겨찾기 기능을 구현하는데 있어서 이 Class를 사용하여
* Activity의 onCreate에서 이 객체를 호출하여 동기화를 한다.
* 두 번째는 서버와의 통신에서 사용된다. 이 Class에서는 거의 모든 Activity에서 원하는
* 상점데이터 및 물품데이터 정보를 서버에서 받아와 관리하고 전달한다.*/

public class CommunicationManager {
    private Product product;
    private Store store;
    private ArrayList<Product> favorit_products = new ArrayList<>();
    private ListProdouctAdapter adapter;
    private  ArrayList<Product> products;

    private static volatile CommunicationManager communicationManager = null;

    private CommunicationManager() {}

    //CommunicationManager의 생성자이며 싱글턴 패턴을 위해 아래와 같이 구현했다.
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

    //상점정보를 받아오는  메서드
    public ArrayList<Store> initStore(){
        ArrayList<Store> stores = new ArrayList<>();
        if(products == null){
            return stores;
        }
        for(int i = 0;i<10;i++)
        {
            stores.add(new Store(products.get(i).getLocation(), products.get(i).getSale()));
        }

        return stores;
    }

    //물품정보를 받아오는 메서드
    public ArrayList<Product> initProduct(){
        ArrayList<Product> products = new ArrayList<>();
        if(this.products == null){
            return products;
        }
        for(int i = 0;i<10;i++)
        {
            products.add(new Product(this.products.get(i).getProductName(), this.products.get(i).getPrice()));
        }

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

    //아이템의 이름을 매개변수로 주고 Arraylist를 받는 메서드
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

    //해당 아이템을 싱글턴패턴객체에 저장하는 메서드,
    //서버에서 받아온 물품정보들을 싱글턴 패턴에 저장한다.
    public void setProducts(ArrayList products)
    {
        this.products=products;
    }

    //해당 아이템을 싱글턴패턴객체에 저장하는 메서드,
    //product를 클릭할 시 해당 객체를 CommunucationManager에 저장하고,
    //다른 Activity에서 접근하여 사용할수 있게한다.
    public void addFavoirtProduct(Product product){
        this.favorit_products.add(product);
    }

    //즐겨찾기가 저장된 favorit_products를 get
    public ArrayList<Product> getFavorit_products() {
        return favorit_products;
    }

    //해당 아이템을 싱글턴패턴객체에 저장하는 메서드,
    //store 클릭할 시 해당 객체를 CommunucationManager에 저장하고,
    //다른 Activity에서 접근하여 사용할수 있게한다.
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

}

//해당 클래스는 list를 가격순으로 정렬하기 위해 제작하였다.
class AscendingInteger implements Comparator<Store> {

    @Override
    public int compare(Store o1, Store o2) {
        int o1Data, o2Data;

        o1Data = Integer.parseInt(o1.explanation);
        o2Data = Integer.parseInt(o2.explanation);

        return Integer.compare(o1Data, o2Data);
    }
}