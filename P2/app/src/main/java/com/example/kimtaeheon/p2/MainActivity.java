package com.example.kimtaeheon.p2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    ViewPager viewPager;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    EditText editText;
    Toast toast;
    CommunicationManager communicationManager;
    private static Retrofit retrofit;
    private static RetrofitExService retrofitExService;
    private static ArrayList<Product> products = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //서버에서 정보를 받아옴
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitExService = retrofit.create(RetrofitExService.class);
        retrofitExService.getItem().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    products.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        editText=(EditText)findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        communicationManager = CommunicationManager.getInstance();
        Log.e("Num",""+products.size());
        communicationManager.setProducts(products);

        //viewpager에 adapter를 붙여준다.
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("");

        this.setTitle("");
       CommunicationManager.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //메뉴에 있는 버튼을 클릭할 시 edittext에 있는 정보를 받아서
    //검색을 하고, 해당 activity를 실시한다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_main_serach){
            String search = editText.getText().toString();
            CommunicationManager.getInstance().changeActivityProduct(new Product(search, ""));
            Intent intent = new Intent(this, ProdouctDetailActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //내부 클래스로 FragmentStatePagerAdapter를 상속받아서 정의해준다.
    class MyPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments=new ArrayList<>();
        String titles[]=new String[]{"", "",""};
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
            fragments.add(new TwoFragment());
            fragments.add(new ThreeFragment());
            ThreeFragment f = new ThreeFragment();
            f.opt = Product.OPT.MIUS;
            fragments.add(f);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 3) {
                ThreeFragment f = (ThreeFragment) fragments.get(position);
                f.adapter.notifyItemRangeChanged(0, f.adapter.getItemCount());
                return f;
            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}











