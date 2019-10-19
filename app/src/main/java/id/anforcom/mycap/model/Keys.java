package id.anforcom.mycap.model;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public enum Keys {

    BUNDLE("bundle"),
    CODE("code"),
    EN_US("en-US"),
    FCM_TOKEN("fcm_token"),
    BAHASA_ID("id-ID"),
    ID_GROUP("id_group"),
    ID_USER("id_user"),
    NAMA_USER("nama_user"),
    PILIHAN_BAHASA("pilihan_bahasa");

    private String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
