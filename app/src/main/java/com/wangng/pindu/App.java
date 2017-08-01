package com.wangng.pindu;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 小爱 on 2017/3/12.
 */

public class App extends Application {

    private static App mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Stetho.initializeWithDefaults(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public static App getApp() {
        return mApp;
    }
}
