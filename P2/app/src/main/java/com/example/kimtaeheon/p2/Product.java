package com.example.kimtaeheon.p2;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Product {
    public final String productName;
    public final String date;
    public final String price;
    public final String location;
    public final String brand;
    public final String sale;
    public final String opo;


    public Product(String productName, String date, String price, String location, String brand, String sale, String opo) {
        this.productName = productName;
        this.date = date;
        this.price = price;
        this.location = location;
        this.brand = brand;
        this.sale = sale;
        this.opo = opo;
    }
    public String getProductName()
    {
        return productName;
    }
    public String getPrice()
    {
        return price;
    }
    public String getLocation()
    {
        return location;
    }
    public String getDate()
    {
        return date;
    }
    public String getBrand()
    {
        return brand;
    }
    public String getSale()
    {
        return sale;
    }
    public String getOpo()
    {
        return opo;
    }
}

class ListProductHolder extends RecyclerView.ViewHolder{
    public ImageView image;
    public TextView name;
    public TextView explan;
    public ImageView opt;
    public View root;

    public ListProductHolder(View root) {
        super(root);
        image=(ImageView)root.findViewById(R.id.list_prodouct_image);
        name=(TextView)root.findViewById(R.id.list_prodouct_name);
        explan=(TextView)root.findViewById(R.id.list_prodouct_explanation);
        opt=(ImageView)root.findViewById(R.id.list_store_opt);
        this.root=(View)root;
    }
}

class ListProdouctAdapter extends RecyclerView.Adapter<ListProductHolder>{
    Context context;
    int resId;
    ArrayList<Product> products;

    public ListProdouctAdapter(Context context, int resId, ArrayList<Product> products) {
        this.context = context;
        this.resId = resId;
        this.products = products;
    }

    public void changeItem(){
        for(int i = 0; i < this.getItemCount(); i++){
            this.notifyItemChanged(i);
        }
    }

    @NonNull
    @Override
    public ListProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
        return new ListProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductHolder listProductHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
