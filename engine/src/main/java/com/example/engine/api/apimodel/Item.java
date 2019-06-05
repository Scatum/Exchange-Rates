package com.example.engine.api.apimodel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Item {

    /*
     * to get values from map
     * */
    public static final String NON_CASH = "0";
    public static final String CASH = "1";
    public static final String BUY = "buy";
    public static final String SELL = "sell";
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String RUR = "RUR";


    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("date")
    private long date;


    @SerializedName("logo")
    private String logo;


    @SerializedName("list")
    private Map<String, Map<String, Map<String, JsonElement>>> list;

    public Item(String title, long date, String logo, Map<String, Map<String, Map<String, JsonElement>>> list) {
        this.title = title;
        this.date = date;
        this.logo = logo;
        this.list = list;
    }

    public Item() {
    }


    public Map<String, Map<String, Map<String, JsonElement>>> getList() {
        return list;
    }

    public void setList(Map<String, Map<String, Map<String, JsonElement>>> list) {
        this.list = list;
    }

    public JsonElement getCurrency(String currencyType, String cashType, String buyOrSell) {
        if (list.get(currencyType) != null && list.get(currencyType).get(cashType) != null) {
            return list.get(currencyType).get(cashType).get(buyOrSell);
        }
        return null;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public class CurrencyModel {

        @SerializedName("buy")
        private double buy;

        @SerializedName("sell")
        private double sell;


        public double getBuy() {
            return buy;
        }

        public void setBuy(double buy) {
            this.buy = buy;
        }

        public double getSell() {
            return sell;
        }

        public void setSell(double sell) {
            this.sell = sell;
        }
    }


}
