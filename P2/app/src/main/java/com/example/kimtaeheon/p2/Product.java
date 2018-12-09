package com.example.kimtaeheon.p2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Product {
    public enum OPT{NONE, PLUS, MIUS};

    public String name;
    public final String productName;
    public final String date;
    public final String price;
    public final String location;
    public final String brand;
    public final String sale;
    public final String opo;
    public String exlpan;


    public Product(String productName, String date, String price, String location, String brand, String sale, String opo) {
        this.productName = productName;
        this.date = date;
        this.price = price;
        this.location = location;
        this.brand = brand;
        this.sale = sale;
        this.opo = opo;
    }

    public Product(String productName, String exlpan) {
        this(productName, "", "", "", "", "", "");
        this.exlpan = exlpan;
        this.name = productName;
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
        opt=(ImageView)root.findViewById(R.id.list_prodouct_opt);
        this.root=(View)root;
    }
}

class ListProdouctAdapter extends RecyclerView.Adapter<ListProductHolder>{
    Context context;
    int resId;
    ArrayList<Product> products;
    Product.OPT opt;

    public ListProdouctAdapter(Context context, int resId, ArrayList<Product> products, Product.OPT opt) {
        this.context = context;
        this.resId = resId;
        this.products = products;
        this.opt = opt;
        if(this.opt == null){
            this.opt = Product.OPT.PLUS;
        }
    }

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
    public void onBindViewHolder(@NonNull final ListProductHolder listProductHolder, int i) {
        final Product product = products.get(i);
        final int index = i;

        listProductHolder.name.setText(product.name);
        listProductHolder.explan.setText(product.exlpan);

        /*
        if(product.name.equals("sdfa")){
            listProductHolder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_doc, null));
        }else if(product.name.equals("asdfdsaf")){
            listProductHolder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_file, null));
        }else if(product.name.equals("sadfsfad")){
            listProductHolder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_image, null));
        }
        */
        if(opt == Product.OPT.PLUS){
            listProductHolder.opt.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.plus, null));
        }else if(opt == Product.OPT.MIUS){
            listProductHolder.opt.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.minus, null));
        }


        listProductHolder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CommunicationManager.getInstance().changeActivityProduct(product);
                Intent intent = new Intent(context, ProdouctDetailActivity.class);
                context.startActivity(intent);
            }
        });
        listProductHolder.opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opt == Product.OPT.PLUS){
                    Toast toast = Toast.makeText(context, product.name + "is plus", Toast.LENGTH_SHORT);
                    toast.show();
                    CommunicationManager.getInstance().addFavoirtProduct(product);
                }else if(opt == Product.OPT.MIUS) {
                    Toast toast = Toast.makeText(context, product.name + "is minus", Toast.LENGTH_SHORT);
                    toast.show();
                    products.remove(index);
                    notifyItemRemoved(index);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}
