package com.salieri.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.salieri.baselib.core.BaseApplication;
import com.salieri.baselib.task.FUNC;

import java.util.HashMap;
import java.util.Map;

public class PrefFuncUtil {
    private SharedPreferences sf;
    private Gson gson = new Gson();
    private PrefFuncUtil() {
        sf = BaseApplication.getInstance().getSharedPreferences("logo_pref", Context.MODE_PRIVATE);
    }

    private static PrefFuncUtil instance;
    public static PrefFuncUtil getInstance() {
        if (instance == null) instance = new PrefFuncUtil();
        return instance;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putFunc(String key, FUNC.Content value) {
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(key, gson.toJson(value));
        editor.apply();
    }

    public FUNC.Content getFunc(String key) {
        if (sf.contains(key)) {
            String json = sf.getString(key, "");
            return gson.fromJson(json, FUNC.Content.class);
        }
        return null;
    }

    public String getString(String key) {
        return sf.getString(key, "");
    }

    public Map<String, FUNC.Content> getAllFunc() {
        Map<String, FUNC.Content> map = new HashMap<>();
        for (String key : sf.getAll().keySet()) {
            FUNC.Content content = getFunc(key);
            if (content != null) map.put(key, content);
        }
        return map;
    }
}
