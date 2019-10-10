package id.anforcom.mycap.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aflah on 21/08/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ConverterUtils {
    public static String object2StringJSON(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static Object stringJson2Object(String stringJSON, Class cls) {
        Gson gson = new Gson();
        return gson.fromJson(stringJSON, cls);
    }

    public static List<Object> stringJson2ObjectList(String stringJSON, Class cls) {
        List<Object> objectList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(stringJSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                objectList.add(stringJson2Object(jsonArray.getString(i), cls));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objectList;
    }

    public static String objectList2String(List<Object> objectList) {
        Gson gson = new Gson();
        String json = gson.toJson(objectList);
        return json;
    }
}
