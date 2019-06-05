package com.example.engine.serviceImpl;

import android.util.Log;

import com.example.engine.api.ApiRequest;
import com.example.engine.api.apimodel.DetailItem;
import com.example.engine.api.apimodel.Item;
import com.example.engine.service.RatesService;
import com.google.gson.Gson;
import com.mix.notificationcenter.NotificationCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class RatesServiceImpl extends BaseService implements RatesService {

    @Override
    public void getRatesList() {
        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
        Call<Map<String, Item>> call = apiRequest.ratesList("en");
        call.enqueue(new Callback<Map<String, Item>>() {
            @Override
            public void onResponse(Call<Map<String, Item>> call, retrofit2.Response<Map<String, Item>> response) {

                if (response.isSuccessful()) {
                    List<Item> items = ItemsResponseToList(response.body());
                    NotificationCenter.INSTANCE.postNotificationName(
                            NotificationCenter.Type.NETWORK_EXCHANGE_RATES_LIST, items);

                } else {
                    NotificationCenter.INSTANCE.postNotificationName(
                            NotificationCenter.Type.NETWORK_EXCHANGE_RATES_LIST_ERROR, "Something Went Wrong");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Item>> call, Throwable t) {

                NotificationCenter.INSTANCE.postNotificationName(
                        NotificationCenter.Type.NETWORK_EXCHANGE_RATES_LIST_ERROR,
                        "Something went wrong : Error -> " + t.getMessage());
            }
        });
    }

    @Override
    public void getBankDetail(String organizationId) {
        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
        Call<DetailItem> call = apiRequest.bankDetail(organizationId);
        call.enqueue(new Callback<DetailItem>() {
            @Override
            public void onResponse(Call<DetailItem> call, retrofit2.Response<DetailItem> response) {
                if (response.isSuccessful()) {
                    NotificationCenter.INSTANCE.postNotificationName(
                            NotificationCenter.Type.NETWORK_BANK_DETAIL, response.body());

                } else {
                    NotificationCenter.INSTANCE.postNotificationName(
                            NotificationCenter.Type.NETWORK_BANK_DETAIL_ERROR, "Something Went Wrong");
                }
            }

            @Override
            public void onFailure(Call<DetailItem> call, Throwable t) {
                NotificationCenter.INSTANCE.postNotificationName(
                        NotificationCenter.Type.NETWORK_BANK_DETAIL_ERROR, t.getMessage());
            }
        });
    }

    private List<Item> ItemsResponseToList(Map<String, Item> body) {
        List<Item> list = new ArrayList<Item>(body.values());
        List<String> keys = new ArrayList<>();
        keys.addAll(body.keySet());
        for (int i = 0; i < keys.size(); i++) {
            list.get(i).setId(keys.get(i));
        }
        return list;

    }

}
