package com.wangng.pindu.httpclient;

import android.os.Build;
import android.util.Log;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by tanzhh on 2016/4/14.
 */
public class CMOkHttpClientFactory extends OkHttpClientFactory {

    private static OkHttpClient globalOkHttpClient;

    private static CMOkHttpClientFactory instance;

    public static CMOkHttpClientFactory getInstance() {
        if (instance == null) {
            instance = new CMOkHttpClientFactory();
        }
        return instance;
    }

    public static OkHttpClient getGlobalOkHttpClient() {
        if (globalOkHttpClient == null) {
            globalOkHttpClient = getInstance().createOkHttpClientBuilder().build();
        }
        return globalOkHttpClient;
    }

    @Override
    protected void authenticator(OkHttpClient.Builder builder) {
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                String token = "token";
                String deviceId = "xiaomi";
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("devieceId", deviceId)
                        .header("deviceId", deviceId)
                        .header("deviceName", Build.MODEL)
                        .header("version", "4.1.3")
                        .header("ClientType", "android");
                if (originalRequest.body() == null) {
                    requestBuilder.header("Content-Type", "text/json");
                }
                return chain.proceed(requestBuilder.build());
            }
        });
        builder.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                try {
                    return response.request().newBuilder()
                            .header("Authorization", "Bearer " + "token")
                            .build();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IOException(e.getMessage());
                }
            }
        });
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                Log.d("LogIntercepter","request"+request.toString());
                Log.d("LogIntercepter","response"+response.body().toString());
                return response;
            }
        });
    }
}
