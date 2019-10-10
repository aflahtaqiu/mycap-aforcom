package id.anforcom.mycap.data.remote.api;

import com.google.gson.JsonObject;

import id.anforcom.mycap.model.ResponseFirebaseMessaging;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by aflah on 07/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface IFirebaseApi {

    @POST("/fcm/send")
    Call<ResponseFirebaseMessaging> sendFirebaseMessaging(
            @Header("Authorization") String authorizationKey,
            @Body JsonObject jsonObject
    );
}
