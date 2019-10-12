package id.anforcom.mycap.data.remote.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By aflah on 26/05/19.
 * Email : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */

public class ApiRetrofit {

    public static final String BASE_URL = "https://mycap.herokuapp.com/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
