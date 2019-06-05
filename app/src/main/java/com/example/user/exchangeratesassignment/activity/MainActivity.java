package com.example.user.exchangeratesassignment.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.engine.api.apimodel.Item;
import com.example.user.exchangeratesassignment.ConstantValues.ConstantValues;
import com.example.user.exchangeratesassignment.R;
import com.example.user.exchangeratesassignment.adapter.RatesListAdapter;
import com.example.user.exchangeratesassignment.base.BaseActivity;
import com.example.user.exchangeratesassignment.manager.ScreenManager;
import com.example.user.exchangeratesassignment.util.Util;
import com.mix.notificationcenter.NotificationCenter;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

import static android.media.CamcorderProfile.get;

public class MainActivity extends BaseActivity implements RatesListAdapter.ItemClickListener, AdapterView.OnItemSelectedListener {
    private RecyclerView ratesListView = null;
    private RatesListAdapter ratesListAdapter = null;
    private Spinner cashOptionSpinner = null;
    private Spinner currencyOptionSpinner = null;
    private static final String CURRENCY_OPTION = "currencyOptionSpinner";
    private static final String CASH_OPTION = "cashOptionSpinner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCashOptionSpinner();
        initCurrencyOptionSpinner();
        initRatesListView();
        registerNotificationCenter();
        showLoading();
        getRatesService().getRatesList();
    }


    @Override
    protected void onDestroy() {
        unregisterNotificationCenter();
        hideLoading();
        super.onDestroy();
    }

    private void registerNotificationCenter() {
        NotificationCenter.INSTANCE.addObserver(this,
                NotificationCenter.Type.NETWORK_EXCHANGE_RATES_LIST, o -> {
                    List<Item> items = (List<Item>) o;
                    ratesListAdapter.setData(items);
                    hideLoading();
                    return Unit.INSTANCE;

                });

        NotificationCenter.INSTANCE.addObserver(this,
                NotificationCenter.Type.NETWORK_EXCHANGE_RATES_LIST_ERROR, o -> {
                    hideLoading();
                    return Unit.INSTANCE;

                });
    }

    private void unregisterNotificationCenter() {
        NotificationCenter.INSTANCE.removeObserver(this);


    }

    private void initCurrencyOptionSpinner() {
        currencyOptionSpinner = findViewById(R.id.currency_option);
        currencyOptionSpinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(Item.USD);
        categories.add(Item.EUR);
        categories.add(Item.RUR);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyOptionSpinner.setAdapter(dataAdapter);
        currencyOptionSpinner.setTag(CURRENCY_OPTION);
    }

    private void initCashOptionSpinner() {
        cashOptionSpinner = findViewById(R.id.cash_option);
        cashOptionSpinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(ConstantValues.CASH);
        categories.add(ConstantValues.NON_CASH);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cashOptionSpinner.setAdapter(dataAdapter);
        cashOptionSpinner.setTag(CASH_OPTION);


    }

    private void initRatesListView() {
        ratesListView = findViewById(R.id.ratesListView);
        ratesListView.setLayoutManager(new LinearLayoutManager(this));
        ratesListAdapter = new RatesListAdapter(this, new ArrayList<>(),
                Util.getCurrencyTypeBySpinnerPosition(currencyOptionSpinner.getSelectedItemPosition()),
                Util.getCashTypeBySpinnerPosition(cashOptionSpinner.getSelectedItemPosition()),
                this);
        ratesListView.setAdapter(ratesListAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onContactSelected(Item item, View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(ConstantValues.ORGANIZATION_ID, item.getId());
        intent.putExtra(ConstantValues.TITLE, item.getTitle());
        ScreenManager.get().openScreen(this, intent, view);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (CASH_OPTION.equals(parent.getTag())) {
            ratesListAdapter.configureCashType(Util.getCashTypeBySpinnerPosition(position));

        } else if (CURRENCY_OPTION.equals(parent.getTag())) {
            ratesListAdapter.configureCurrencyType(Util.getCurrencyTypeBySpinnerPosition(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //spinner listener
    }
}
