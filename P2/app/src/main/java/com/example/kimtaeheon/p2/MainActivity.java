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

/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */
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
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitExService = retrofit.create(RetrofitExService.class);
        retrofitExService.getItem().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.e("start","");
                    products.addAll(response.body());
                    /*if (products != null) {
                        for (int i = 0; i < 20; i++) {
                            Log.e("data" + i, products.get(i).getProductName() + "");
                            Log.e("data" + i, products.get(i).getDate() + "");
                            Log.e("data" + i, products.get(i).getPrice() + "");
                            Log.e("data" + i, products.get(i).getLocation() + "");
                            Log.e("data" + i, products.get(i).getBrand() + "");
                            Log.e("data" + i, products.get(i).getSale() + "");
                            Log.e("data" + i, products.get(i).getOpo() + "");
                        }

                    }*/
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
            sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        communicationManager = CommunicationManager.getInstance();
        communicationManager.setProducts(products);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_main_serach){
            String search = editText.getText().toString();
            toast = Toast.makeText(MainActivity.this, search, Toast.LENGTH_SHORT);
            toast.show();
            CommunicationManager.getInstance().changeActivityProduct(new Product(search, ""));
            Intent intent = new Intent(this, ProdouctDetailActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

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
            Fragment f = fragments.get(position);


            return f;
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











