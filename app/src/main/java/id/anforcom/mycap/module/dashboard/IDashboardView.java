package id.anforcom.mycap.module.dashboard;

/**
 * Created by aflah on 04/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface IDashboardView {
    void moveConferenceListener(String code, String idGroup);
    void moveConferenceSpeaker(String code, String idGroup);
    void moveChatRoom(String code, String idGroup);
    void moveLiveTranscibe();

    void showMessage(String message);
    void showProgress(String message);
    void hideProgress ();
}
