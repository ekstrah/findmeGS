package com.example.kimtaeheon.p2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.internal.ListenerHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*Store은 client에서 다루는 상점들의 정보를 담고 있는 class이다.*/
public class Store {

    public enum OPT{NONE, PLUS, MIUS};

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

//ListstoreAdapter은 RecyclerView구현에 있어서 효윻적으로 view에 접근하기 위해서
//view의 아이디들을 기억해 놓는 class이다. 해당 class를 통해서
//view들의 id들을 찾을 필요도 없어서 코드 길이가 줄어들고, 성능향상도 기대 할 수 있다.
class ListStoreAdapter  extends RecyclerView.Adapter<ListStoreHolder>{
    Context context;
    int resId;
    ArrayList<Store> stores;
    Store.OPT opt;

    public ListStoreAdapter(Context context, int resource, ArrayList<Store> stores){
        this(context, resource, stores, Store.OPT.NONE);
    }

    //recyclerview의 구현을 위한 adapter이다.
    public ListStoreAdapter(Context context, int resource, ArrayList<Store> stores, Store.OPT opt) {
        this.context = context;
        this.resId = resource;
        this.stores = stores;
        this.opt = opt;
        if(this.opt == null){
            this.opt = Store.OPT.NONE;
        }
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

    //해당 메소드에서 각 상점들을 정의해준다.
    //list에 있는 각 store 이름, 설명, 사진 등을 item에 대입하고,
    //해당 item에 onClickListener도 설정해준다.
    @Override
    public void onBindViewHolder(@NonNull ListStoreHolder listStoreHolder, final int i) {
        int index = i;
        final Store store = stores.get(i);

        listStoreHolder.name.setText(store.name);
        listStoreHolder.explan.setText(store.explanation);

        listStoreHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreDetailActivity.class);
                CommunicationManager.getInstance().changeActivityStore(store);
                context.startActivity(intent);
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
        outRect.set(20, 10, 20, 10);

        view.setBackgroundColor(0xFFECE9E9);
        ViewCompat.setElevation(view, 20.0f);
    }
}