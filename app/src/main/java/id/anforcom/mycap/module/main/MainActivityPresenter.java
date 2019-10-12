package id.anforcom.mycap.module.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class MainActivityPresenter {

    IMainActivityView view;

    MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    void setBahasa () {
        SharedPrefUtils.setBooleanSharedPref(Keys.ID.getKey(), true);
        SharedPrefUtils.setBooleanSharedPref(Keys.ENGLISH.getKey(), false);
    }

    void setEnglish () {
        SharedPrefUtils.setBooleanSharedPref(Keys.ID.getKey(), false);
        SharedPrefUtils.setBooleanSharedPref(Keys.ENGLISH.getKey(), true);
    }

    void saveDataToSharedPreference (String userName) {
        SharedPrefUtils.setStringSharedPref(Keys.NAMA_USER.getKey(), userName);
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        InstanceIdResult result = task.getResult();

                        if (!task.isSuccessful()) {
                            return;
                        }

                        if (result != null) {
                            Log.e("fcm token", result.getToken());
                            view.moveToDashboard();
                        }
                    }
                });
    }
}
