package id.anforcom.mycap.module;

import android.app.Application;

import com.facebook.stetho.Stetho;

import id.anforcom.mycap.R;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        String appName = getResources().getString(R.string.app_name);
        SharedPrefUtils.initSharedPref(appName, this);
    }
}
