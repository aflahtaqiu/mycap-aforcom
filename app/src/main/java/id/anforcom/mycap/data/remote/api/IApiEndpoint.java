package id.anforcom.mycap.data.remote.api;

import com.google.gson.JsonObject;

import java.util.List;

import id.anforcom.mycap.model.Group;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created By aflah on 26/05/19.
 * Email : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */

public interface IApiEndpoint {

    @GET("group")
    Call<List<Group>> getAllGroup(); //200

    @GET("group/{id}")
    Call<Group> getGroupById(
            @Path("id") String id
    ); //200

    @POST("group")
    Call<Group> createGroup(
            @Body JsonObject jsonObject
    ); //201

    @POST("group/join")
    Call<Group> joinGroup (@Body JsonObject jsonObject); //201

    @POST("group/left")
    Call<Group> leftGroup(@Body JsonObject jsonObject); //201
}
