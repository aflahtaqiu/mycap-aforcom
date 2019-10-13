package id.anforcom.mycap.data.remote;

import com.google.gson.JsonObject;

import java.util.List;

import id.anforcom.mycap.base.BaseRemoteDataSource;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aflah on 12/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ApiRemoteDataSource extends BaseRemoteDataSource implements ApiDataSource {

    private static ApiRemoteDataSource remoteDataSource;

    private static int RESPONSE_OK = 200;
    private static int RESPONSE_CREATED = 201;
    private static int RESPONSE_NOT_FOUND = 404;

    private static String ERROR_GET_ALL_GROUP = "Maaf tidak ada data group";
    private static String ERROR_GET_GROUP_BY_ID = "Maaf gagal mendapatkan group dengan id " ;
    private static String ERROR_CREATE_GROUP = "Maaf gagal membuat group";
    private static String ERROR_JOIN_GROUP = "Maaf terjadi kesalahan dalam join group.";
    private static String ERROR_NOT_FOUND_CODE = "Code Anda salah. Pastikan kembali code yang Anda masukkan";
    private static String ERROR_LEFT_GROUP = "Maaf terjadi kesalahan dalam left group";

    private static String KEY_ADMIN = "admin";
    private static String KEY_GROUP_CODE = "group_code";
    private static String KEY_GROUP_ID = "group_id";
    private static String KEY_NAME = "name";
    private static String KEY_USER_ID = "user_id";

    public static ApiRemoteDataSource getInstance() {
        if (remoteDataSource == null) {
            remoteDataSource = new ApiRemoteDataSource();
        }
        return remoteDataSource;
    }

    @Override
    public void getAllGroup(GroupListCallback callback) {
        Call<List<Group>> call = apiEndpoint.getAllGroup();
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.code() == RESPONSE_OK)
                    callback.onSuccess(response.body());
                else callback.onError(ERROR_GET_ALL_GROUP);
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getGroupById(String idGroup, GroupCallback callback) {
        Call<Group> call = apiEndpoint.getGroupById(idGroup);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.code() == RESPONSE_OK)
                    callback.onSuccess(response.body());
                else callback.onError(ERROR_GET_GROUP_BY_ID + idGroup);
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void createGroup(String groupCode, String adminName, GroupCallback callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_GROUP_CODE, groupCode);
        jsonObject.addProperty(KEY_ADMIN, adminName);

        Call<Group> call = apiEndpoint.createGroup(jsonObject);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.code() == RESPONSE_CREATED)
                    callback.onSuccess(response.body());
                else {
                    callback.onError(ERROR_CREATE_GROUP);
                }

            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void joinGroup(String userName, String groupCode, GroupCallback callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_NAME, userName);
        jsonObject.addProperty(KEY_GROUP_CODE, groupCode);

        Call<Group> call = apiEndpoint.joinGroup(jsonObject);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.code() == RESPONSE_CREATED)
                    callback.onSuccess(response.body());
                else if (response.code() == RESPONSE_NOT_FOUND)
                    callback.onError(ERROR_NOT_FOUND_CODE);
                else
                    callback.onError(ERROR_JOIN_GROUP);
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void leftGroup(String userId, String groupId, GroupCallback callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_USER_ID, userId);
        jsonObject.addProperty(KEY_GROUP_ID, groupId);

        Call<Group> call = apiEndpoint.leftGroup(jsonObject);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.code() == RESPONSE_CREATED)
                    callback.onSuccess(response.body());
                else
                    callback.onError(ERROR_LEFT_GROUP);
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
