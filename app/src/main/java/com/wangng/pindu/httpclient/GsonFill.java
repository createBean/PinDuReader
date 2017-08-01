package com.wangng.pindu.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by tanzhh on 2016/4/18.
 */
public class GsonFill {
	public static <T> void fill(GsonBuilder gsonBuilder, String jsonString, final T obj) {
		InstanceCreator<T> creator = new InstanceCreator<T>() {
			public T createInstance(Type type) {
				return obj;
			}
		};
		Gson gson = gsonBuilder.registerTypeAdapter(obj.getClass(), creator).create();
		gson.fromJson(jsonString, obj.getClass());
	}
}
