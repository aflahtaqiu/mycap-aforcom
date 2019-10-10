package id.anforcom.mycap.model;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public enum Keys {

    BUNDLE("bundle"),
    CODE("code"),
    ID("bahasa_indonesia"),
    ENGLISH("english"),
    PILIHAN_BAHASA("pilihan_bahasa");

    private String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
