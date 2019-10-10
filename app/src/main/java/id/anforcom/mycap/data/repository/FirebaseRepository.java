package id.anforcom.mycap.data.repository;

import com.google.gson.JsonObject;

import id.anforcom.mycap.data.remote.FirebaseRemoteDataSource;
import id.anforcom.mycap.data.source.FirebaseSource;

/**
 * Created by aflah on 24/07/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class FirebaseRepository implements FirebaseSource {

    private FirebaseRemoteDataSource firebaseRemoteDataSource;

    private static FirebaseRepository firebaseRepository;

    public static FirebaseRepository getInstance() {
        if (firebaseRepository == null) {
            firebaseRepository = new FirebaseRepository(FirebaseRemoteDataSource.getInstance());
        }

        return firebaseRepository;
    }

    private FirebaseRepository(FirebaseRemoteDataSource firebaseRemoteDataSource) {
        this.firebaseRemoteDataSource = firebaseRemoteDataSource;
    }

    @Override
    public void send(String firebaseKey, JsonObject jsonObject, OnSendCallback callback) {
        firebaseRemoteDataSource.send(firebaseKey, jsonObject, callback);
    }
}
