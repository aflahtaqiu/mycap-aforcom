package id.anforcom.mycap.module.splash_screen;

/**
 * Created by aflah on 01/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class SplashScreenPresenter {

    private ISplashScreen view;

    SplashScreenPresenter(ISplashScreen view) {
        this.view = view;
    }

    void moveToMainActivity () {
        view.moveToMain();
    }
}
