package id.anforcom.mycap.module.main;

import id.anforcom.mycap.base.IBaseView;

/**
 * Created by aflah on 03/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface IMainActivityView extends IBaseView {
    void moveToDashboard();

    void showEmptyFieldAlert(String message);
}
