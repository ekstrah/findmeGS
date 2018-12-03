package com.example.kimtaeheon.p2;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.ListenerHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Store {
    String name;
    ArrayList<Product> products;
    String explanation;
    double latitude;
    double longitude;
    String  price;

    public Store(){}

    public Store(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }
}

class ListStoreHolder extends RecyclerView.ViewHolder{
    public ImageView image;
    public TextView name;
    public TextView explan;
    public ImageView opt;
    public View root;

    public ListStoreHolder(View root){
        super(root);
        image=(ImageView)root.findViewById(R.id.list_store_image);
        name=(TextView)root.findViewById(R.id.list_store_name);
        explan=(TextView)root.findViewById(R.id.list_store_explanation);
        opt=(ImageView)root.findViewById(R.id.list_store_opt);
        this.root=(View)root;
    }
}

class ListStoreAdapter  extends RecyclerView.Adapter<ListStoreHolder>{
    Context context;
    int resId;
    ArrayList<Store> stores;


    public ListStoreAdapter(Context context, int resource, ArrayList<Store> stores) {
        this.context = context;
        this.resId = resource;
        this.stores = stores;
    }

    public void changeItem(){
        for(int i = 0; i < this.getItemCount(); i++){
            this.notifyItemChanged(i);
        }
    }

    @NonNull
    @Override
    public ListStoreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
        return new ListStoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStoreHolder listStoreHolder, final int i) {
        int index = i;
        final Store store = stores.get(i);

        listStoreHolder.name.setText(store.name);
        listStoreHolder.explan.setText(store.explanation);

        if(store.name.equals("GS")){
            listStoreHolder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_doc, null));
        }else if(store.name.equals("CU")){
            listStoreHolder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_file, null));
        }else if(store.name.equals("Sibal")){
            listStoreHolder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_image, null));
        }

        listStoreHolder.opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(context, store.name+" add favorit", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() { return stores.size();}


}

class MyItemDecoration extends RecyclerView.ItemDecoration{
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(20, 20, 20, 20);

        view.setBackgroundColor(0xFFECE9E9);
        ViewCompat.setElevation(view, 20.0f);
    }
}