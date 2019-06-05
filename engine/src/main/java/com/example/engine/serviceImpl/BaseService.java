package com.example.engine.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class BaseService {
    private Gson gson = new GsonBuilder().setLenient().create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rate.am/ws/mobile/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
