package id.anforcom.mycap.module.main;

import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class MainActivityPresenter {

    IMainActivityView view;

    MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    void setBahasa () {
        SharedPrefUtils.setBooleanSharedPref(Keys.ID.getKey(), true);
        SharedPrefUtils.setBooleanSharedPref(Keys.ENGLISH.getKey(), false);
    }

    void setEnglish () {
        SharedPrefUtils.setBooleanSharedPref(Keys.ID.getKey(), false);
        SharedPrefUtils.setBooleanSharedPref(Keys.ENGLISH.getKey(), true);
    }
}
