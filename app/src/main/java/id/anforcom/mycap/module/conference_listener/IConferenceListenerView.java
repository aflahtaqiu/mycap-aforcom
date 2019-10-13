package id.anforcom.mycap.module.conference_listener;

import id.anforcom.mycap.base.IBaseView;

/**
 * Created by aflah on 10/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface IConferenceListenerView extends IBaseView {

    void moveToDashboard();

    void setSpeakerName (String speakerName);
}
