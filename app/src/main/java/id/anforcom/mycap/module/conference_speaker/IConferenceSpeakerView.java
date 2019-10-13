package id.anforcom.mycap.module.conference_speaker;

import java.util.List;

import id.anforcom.mycap.base.IBaseView;
import id.anforcom.mycap.model.User;

/**
 * Created by aflah on 10/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


interface IConferenceSpeakerView extends IBaseView {

    void setListenerData(List<User> users);
    void moveToDashboard();
}
