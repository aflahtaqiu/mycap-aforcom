package id.anforcom.mycap.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by aflah on 10/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


@Data
public class User {

    @SerializedName("name")
    private String name;

    @SerializedName("_id")
    private String id;
}
