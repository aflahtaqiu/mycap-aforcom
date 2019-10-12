package id.anforcom.mycap.base;

import id.anforcom.mycap.data.remote.api.ApiFirebaseRetrofit;
import id.anforcom.mycap.data.remote.api.ApiRetrofit;
import id.anforcom.mycap.data.remote.api.IApiEndpoint;
import id.anforcom.mycap.data.remote.api.IFirebaseApi;

/**
 * Created by aflah on 09/08/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class BaseRemoteDataSource {
    protected IApiEndpoint apiEndpoint = ApiRetrofit.getInstance().create(IApiEndpoint.class);
    protected IFirebaseApi firebaseApi = ApiFirebaseRetrofit.getInstance().create(IFirebaseApi.class);
}
