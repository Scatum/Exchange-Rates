package com.example.user.exchangeratesassignment.util;
import com.example.engine.api.apimodel.Item;

public class Util {

    public static String getCurrencyTypeBySpinnerPosition(int position) {
        switch (position) {
            case 0:
                return Item.USD;
            case 1:
                return Item.EUR;
            case 2:
                return Item.RUR;
        }
        return Item.USD;
    }

    public static String getCashTypeBySpinnerPosition(int position) {
        switch (position) {
            case 0:
                return Item.CASH;
            case 1:
                return Item.NON_CASH;
        }
        return Item.NON_CASH;
    }
}
