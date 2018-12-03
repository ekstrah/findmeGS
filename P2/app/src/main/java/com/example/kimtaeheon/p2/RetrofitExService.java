package com.example.kimtaeheon.p2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitExService {
    String URL = "http://165.227.135.206:8000";

    @GET("/shop")
    Call<List<Product>> getItem(@Query("productName") String productName);

    @GET("/shop")
    Call<List<Product>> getLocation(@Query("location") String location);

}
