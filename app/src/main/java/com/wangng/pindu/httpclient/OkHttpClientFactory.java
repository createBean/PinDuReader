package com.wangng.pindu.httpclient;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tanzhh on 2016/4/14.
 */
public abstract class OkHttpClientFactory {

	public OkHttpClient.Builder createOkHttpClientBuilder() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(10, TimeUnit.SECONDS);
		authenticator(builder);
		setRetry(builder, 3);
//		setStethoInterceptor(builder);
		return builder;
	}

	protected abstract void authenticator(OkHttpClient.Builder builder);

	protected void setRetry(OkHttpClient.Builder builder, final int tryCount) {
		builder.addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request request = chain.request();
				Response response = chain.proceed(request);
				if ("GET".equals(request.method())) {
					int i = 0;
					while (!response.isSuccessful() && i < tryCount) {
						i++;
						response = chain.proceed(request);
					}
				}
				return response;
			}
		});
	}

//	protected void setStethoInterceptor(OkHttpClient.Builder builder) {
//		builder.addNetworkInterceptor(new StethoInterceptor());
//	}
}
