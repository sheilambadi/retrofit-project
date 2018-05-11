package com.sheilambadi.android.retrofitproject.utils;

import android.app.Application;

/**
 * called whenever application is launched and
 * initiates connectivity receiver listener
 * */

public class ApplicationLaunched extends Application {
    private static ApplicationLaunched instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized ApplicationLaunched getInstance(){
        return instance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
