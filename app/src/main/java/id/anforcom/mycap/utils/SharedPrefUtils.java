package id.anforcom.mycap.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Set;

public class SharedPrefUtils {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void initSharedPref(String prefName, Context context){
        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void setStringSharedPref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static void setIntSharedPref(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setFloatSharedPref(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void setBooleanSharedPref(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setLongSharedPref(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setMapStringSharedPref(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static void setObjectSharedPref(String key, Object object) {
        String value = ConverterUtils.object2StringJSON(object);
        editor.putString(key, value);
        editor.apply();
    }

    public static void setObjectArrayListSharedPref(String key, List<Object> objectList) {
        String value = ConverterUtils.object2StringJSON(objectList);
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringSharedPref(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public static int getIntSharedPref(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public static float getFloatSharedPref(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public static long getLongSharedPref(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public static boolean getBooleanSharedPref(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static Set<String> getSetStringSharedPref(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public static Object getObjectSharedPref(String key, String defValue, Class cls){
        String stringOfObject = sharedPreferences.getString(key, defValue);
        return ConverterUtils.stringJson2Object(stringOfObject, cls);
    }

    public static List<Object> getObjectListSharedPref(String key, Class cls) {
        String defValue = "[]";
        String stringListOfObject = sharedPreferences.getString(key, defValue);
        return ConverterUtils.stringJson2ObjectList(stringListOfObject, cls);
    }

    public static void removeSavedPref(String key) {
        editor.remove(key);
        editor.apply();
    }

    public static void clearAll() {
        editor.clear();
        editor.commit();
    }
}
