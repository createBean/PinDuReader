package com.wangng.pindu.httpclient;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.Date;

public class GsonFactory {

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = createGsonBuilder().create();
        }
        return gson;
    }

    private static Gson getExposeGson;

    public static Gson getExposeGson() {
        if (getExposeGson == null) {
            getExposeGson = createGsonBuilderWithExpose().create();
        }
        return getExposeGson;
    }

    public static GsonBuilder createGsonBuilder() {
        GsonBooleanAsIntTypeAdapter booleanAsIntAdapter = new GsonBooleanAsIntTypeAdapter();
        return new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(com.google.gson.JsonElement p1, java.lang.reflect.Type p2, com.google.gson.JsonDeserializationContext p3) {
                        JsonPrimitive jsonPrimitive = p1.getAsJsonPrimitive();
                        if (jsonPrimitive.isNumber()) {
                            return new Date(jsonPrimitive.getAsLong());
                        } else {
                            return getDate(jsonPrimitive.getAsString());
                        }
                    }
                }).registerTypeAdapter(JSONArray.class, new JsonDeserializer<JSONArray>() {
                    @Override
                    public JSONArray deserialize(com.google.gson.JsonElement p1, java.lang.reflect.Type p2, com.google.gson.JsonDeserializationContext p3) {
                        JSONArray jsonArray = null;
                        try {
                            if (p1.isJsonArray()) {
                                jsonArray = new JSONArray(p1.getAsJsonArray().toString());
                            } else {
                                jsonArray = new JSONArray(p1.getAsString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return jsonArray;
                    }
                }).registerTypeAdapter(JSONObject.class, new JsonDeserializer<JSONObject>() {
                    @Override
                    public JSONObject deserialize(com.google.gson.JsonElement p1, java.lang.reflect.Type p2, com.google.gson.JsonDeserializationContext p3) {
                        JSONObject jsonObject = null;
                        try {
                            if (p1.isJsonObject()) {
                                jsonObject = new JSONObject(p1.getAsJsonObject().toString());
                            } else {
                                jsonObject = new JSONObject(p1.getAsString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return jsonObject;
                    }
                });

    }

    public static GsonBuilder createGsonBuilderWithExpose() {
        return createGsonBuilder().excludeFieldsWithoutExposeAnnotation();
    }

    public static Date getDate(Object obj) {
        return getDate(obj, null);
    }

    public static Date getDate(Object obj, Date defaultValue) {
        Date result = defaultValue;
        Object tmp = obj;
        if (tmp != null) {
            if (tmp instanceof Date) {
                result = (Date) tmp;
            } else if (tmp instanceof Long) {
                try {
                    result = new Date((Long) tmp);
                } catch (Exception e) {
                }
            } else if (tmp instanceof String) {
                String tmpString = (String) tmp;
                try {
                    result = new Date(Long.parseLong(tmpString));
                } catch (Exception e) {
                }
            }
        }
        return result;
    }
}