package id.anforcom.mycap.module.chatroom;

/**
 * Created by aflah on 08/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface IChatRoomView {

    void showMessage(String message);
    void showProgress(String message);
    void hideProgress ();

    void moveToDashboard();
}
