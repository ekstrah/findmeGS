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
    String name;
    int price;
    String explanation;
    ArrayList<Store> stores;

    public Product(String name, int price, String explanation) {
        this.name = name;
        this.price = price;
        this.explanation = explanation;
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
        image=(ImageView)root.findViewById(R.id.list_product_image);
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


}
