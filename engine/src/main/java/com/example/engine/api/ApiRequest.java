package com.example.engine.api;



import com.example.engine.api.apimodel.Item;
import com.example.engine.api.apimodel.DetailItem;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("rates.ashx")
    Call<Map<String, Item>> ratesList(@Query("lang") String lang);

    @GET("branches.ashx")
    Call<DetailItem> bankDetail(@Query("id") String id);
}
