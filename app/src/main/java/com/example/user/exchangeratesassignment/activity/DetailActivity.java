package com.example.user.exchangeratesassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.engine.api.apimodel.Item;
import com.example.user.exchangeratesassignment.ConstantValues.ConstantValues;
import com.example.user.exchangeratesassignment.R;
import com.example.user.exchangeratesassignment.base.BaseActivity;
import com.google.gson.Gson;
import com.mix.notificationcenter.NotificationCenter;

import java.util.List;

import kotlin.Unit;

public class DetailActivity extends BaseActivity {

    private String organizationId = null;
    private String title = null;
    private TextView titleTextView = null;
    private TextView locationTextView = null;
    private TextView addressTextView = null;
    private TextView contactNumberTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        checkIntnent();
        initViews();
        showLoading();
        registerNotificationCenter();
        getRatesService().getBankDetail(organizationId);
    }


    @Override
    protected void onDestroy() {
        NotificationCenter.INSTANCE.removeObserver(this);
        hideLoading();
        super.onDestroy();
    }

    private void checkIntnent() {
        Intent myIntent = getIntent();
        if (myIntent.hasExtra(ConstantValues.ORGANIZATION_ID)) {
            organizationId = myIntent.getStringExtra(ConstantValues.ORGANIZATION_ID);
            title = myIntent.getStringExtra(ConstantValues.TITLE);
        } else {
            Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void registerNotificationCenter() {
        NotificationCenter.INSTANCE.addObserver(this,
                NotificationCenter.Type.NETWORK_BANK_DETAIL, o -> {
                    hideLoading();
                    return Unit.INSTANCE;

                });

        NotificationCenter.INSTANCE.addObserver(this,
                NotificationCenter.Type.NETWORK_BANK_DETAIL_ERROR, o -> {
                    hideLoading();
                    finish();
                    return Unit.INSTANCE;

                });
    }

    private void initViews() {
        titleTextView = findViewById(R.id.title);
        locationTextView = findViewById(R.id.location);
        addressTextView = findViewById(R.id.address);
        contactNumberTextView = findViewById(R.id.contact_number);
        titleTextView.setText(title);
    }
}
