package id.anforcom.mycap.module.dashboard;

/**
 * Created by aflah on 04/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface IDashboardView {
    void moveConferenceListener(String code);
    void moveConferenceSpeaker(String code);
    void moveChatRoom(String code);
    void moveLiveTranscibe();

    void showMessage(String message);
}
