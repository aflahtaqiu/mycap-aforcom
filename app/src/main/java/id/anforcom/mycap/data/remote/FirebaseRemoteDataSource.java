package id.anforcom.mycap.data.remote;

import com.google.gson.JsonObject;

import id.anforcom.mycap.base.BaseRemoteDataSource;
import id.anforcom.mycap.data.remote.api.ApiFirebaseRetrofit;
import id.anforcom.mycap.data.remote.api.IApiEndpoint;
import id.anforcom.mycap.data.source.FirebaseSource;

/**
 * Created by aflah on 24/07/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class FirebaseRemoteDataSource extends BaseRemoteDataSource implements FirebaseSource {

    private static FirebaseRemoteDataSource firebaseRemoteDataSource;

    public static FirebaseRemoteDataSource getInstance() {
        if (firebaseRemoteDataSource == null) {
            firebaseRemoteDataSource = new FirebaseRemoteDataSource();
        }

        return firebaseRemoteDataSource;
    }

    @Override
    public void send(String firebaseKey, JsonObject jsonObject, OnSendCallback callback) {

    }
}
