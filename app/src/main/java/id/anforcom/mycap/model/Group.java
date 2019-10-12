package id.anforcom.mycap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by aflah on 12/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */

@Data
public class Group {

    @SerializedName("_id")
    private String id;

    @SerializedName("group_code")
    private String groupCode;

    @SerializedName("type")
    private String type;

    @SerializedName("admin")
    private User admin;

    @SerializedName("users")
    private List<User> users;
}
