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

/*Product는 client에서 다루는 제품들의 정보를 담고 있는 class이다.*/
public class Product {
    public enum OPT{NONE, PLUS, MIUS};

    public String name;
    public final String proudctName;
    public final String date;
    public final String price;
    public final String location;
    public final String brand;
    public final String sale;
    public final String opo;
    public String exlpan;


    public Product(String proudctName, String date, String price, String location, String brand, String sale, String opo) {
        this.proudctName = proudctName;
        this.date = date;
        this.price = price;
        this.location = location;
        this.brand = brand;
        this.sale = sale;
        this.opo = opo;
    }

    public Product(String proudctName, String exlpan) {
        this(proudctName, "", "", "", "", "", "");
        this.exlpan = exlpan;
        this.name = proudctName;
    }

    public String getProductName()
    {
        return proudctName;
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

//Listprocutholder은 RecyclerView구현에 있어서 효윻적으로 view에 접근하기 위해서
//view의 아이디들을 기억해 놓는 class이다. 해당 class를 통해서
//view들의 id들을 찾을 필요도 없어서 코드 길이가 줄어들고, 성능향상도 기대 할 수 있다.
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

//recyclerview의 구현을 위한 adapter이다.
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

    //해당 메소드에서 각 제품들을 정의해준다.
    //list에 있는 각 product에 이름, 설명, 사진 등을 item에 대입하고,
    //해당 item에 onClickListener도 설정해준다.
    @Override
    public void onBindViewHolder(@NonNull final ListProductHolder listProductHolder, int i) {
        final Product product = products.get(i);
        final int index = i;

        listProductHolder.name.setText(product.name);
        listProductHolder.explan.setText(product.exlpan);

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
                    int a = products.indexOf(product);
                    products.remove(a);
                    notifyItemRemoved( a);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}
