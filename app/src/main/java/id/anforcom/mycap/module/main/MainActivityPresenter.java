package id.anforcom.mycap.module.main;

import android.text.TextUtils;

import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class MainActivityPresenter {

    private IMainActivityView view;

    private static final String EMPTY_STRING = "";

    MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    void setBahasa () {
        SharedPrefUtils.setStringSharedPref(Keys.PILIHAN_BAHASA.getKey(), Keys.BAHASA_ID.getKey());
    }

    void setEnglish () {
        SharedPrefUtils.setStringSharedPref(Keys.PILIHAN_BAHASA.getKey(), Keys.EN_US.getKey());
    }

    void saveDataToSharedPreference (String userName) {
        SharedPrefUtils.setStringSharedPref(Keys.NAMA_USER.getKey(), userName);

        view.hideLoading();
        view.moveToDashboard();
    }

    boolean isLanguageSelectedEmpty () {
        String languageSelected = SharedPrefUtils.getStringSharedPref(Keys.PILIHAN_BAHASA.getKey(), EMPTY_STRING);

        return TextUtils.isEmpty(languageSelected);
    }
}
