package id.anforcom.mycap.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by aflah on 24/07/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */

@Data
public class ResponseFirebaseMessaging {

    @SerializedName("multicast_id")
    private long multicastId;

    @SerializedName("success")
    private int success;

    @SerializedName("failure")
    private int failure;

    @SerializedName("canonical_ids")
    private long canonicalIds;
}
