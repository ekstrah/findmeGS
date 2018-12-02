package com.example.recuclerview_test;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.lab3_recycler);
        List<String> list=new ArrayList<>();
        for(int i=0; i<20; i++){
            list.add("Item = "+i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.addItemDecoration(new MyItemDecoration());
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public MyViewHolder(View itemView){
            super(itemView);
            title=(TextView)itemView.findViewById(android.R.id.text1);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private List<String> list;
        public MyAdapter(List<String> list){
            this.list=list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String text=list.get(position);
            holder.title.setText(text);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
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
}
