package id.anforcom.mycap.data.source;

import com.google.gson.JsonObject;

/**
 * Created by aflah on 24/07/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface FirebaseSource {

    void send(String firebaseKey, JsonObject jsonObject, OnSendCallback callback);

    interface OnSendCallback {
        void onSuccess(boolean isSuccess);
        void onFailure(String errMsg);
    }
}
