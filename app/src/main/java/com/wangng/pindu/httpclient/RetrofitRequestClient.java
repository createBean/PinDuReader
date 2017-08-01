package com.wangng.pindu.httpclient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequestClient {
	protected static RetrofitRequestClient instance;

	public static RetrofitRequestClient getInstance() {
		if (instance == null) {
			instance = new RetrofitRequestClient();
		}
		return instance;
	}

	public <T> T createService(String baseUrl, Class<T> service) {
		Retrofit retrofit = createRetrofitBuilder(baseUrl).build();
		return retrofit.create(service);
	}

	public static Retrofit.Builder createRetrofitBuilder(String baseUrl) {
		baseUrl = baseUrl.replace("\\", "/");
		if (!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		return new Retrofit.Builder()
				.client(CMOkHttpClientFactory.getGlobalOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create(GsonFactory.getGson()))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl(baseUrl);
	}
}