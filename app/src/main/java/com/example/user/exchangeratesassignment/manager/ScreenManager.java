package com.example.user.exchangeratesassignment.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.example.engine.engine.Engine;

public class ScreenManager {

    private static volatile ScreenManager instance = null;

    private ScreenManager() {
    }

    public static ScreenManager get() {
        if (instance == null) {
            synchronized (ScreenManager.class) {
                if (instance == null) {
                    instance = new ScreenManager();
                }
            }
        }
        return instance;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void openScreen(Activity activity, Intent intent, View view) {

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, (View)view, "title");
        activity.startActivity(intent, options.toBundle());
    }

}
