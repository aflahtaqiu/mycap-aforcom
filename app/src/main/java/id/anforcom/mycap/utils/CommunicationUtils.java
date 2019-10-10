package id.anforcom.mycap.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by aflah on 09/08/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class CommunicationUtils {

    public static void changeActivity(Activity activity, Class<?> cls, boolean clearPreviousActivity, Bundle bundle, String key) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null && !TextUtils.isEmpty(key))
            intent.putExtra(key, bundle);
        activity.startActivity(intent);
        if (clearPreviousActivity)
            activity.finish();
    }

    public static void changeActivity(Activity activity, Class<?> cls, boolean clearPreviousActivity) {
        changeActivity(activity, cls, clearPreviousActivity, null, null);
    }

    public static void changeActivity(Activity activity, Class<?> cls) {
        changeActivity(activity, cls, true, null, null);
    }

    public static void changeActivity(Activity activity, Class<?> cls, Bundle bundle, String key) {
        changeActivity(activity, cls, true, bundle, key);
    }

    public static void changeActivity(Activity activity, Class<?> cls, Bundle bundle, String key, boolean clearPreviousActivity) {
        changeActivity(activity, cls, clearPreviousActivity, bundle, key);
    }

    public static void switchFragment(@NonNull FragmentManager fragmentManager,
                                      @IdRes int containerViewId,
                                      @NonNull Fragment fragment,
                                      @Nullable String fragmentTag,
                                      @Nullable String backStackStateName) {
        fragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public static void switchFragment(@NonNull FragmentManager fragmentManager,
                                      @IdRes int containerViewId,
                                      @NonNull Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment)
                .commit();
    }
}
