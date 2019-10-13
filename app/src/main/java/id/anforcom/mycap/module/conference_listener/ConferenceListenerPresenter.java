package id.anforcom.mycap.module.conference_listener;

import id.anforcom.mycap.data.repository.ApiRepository;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.di.Injector;
import id.anforcom.mycap.model.Group;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 10/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class ConferenceListenerPresenter {

    private IConferenceListenerView view;
    private ApiRepository repository;

    private static final String LOADING_MESSAGE = "Loading...";
    private static final String PRE_SPEAKER_NAME = "Speaker : ";

    ConferenceListenerPresenter(IConferenceListenerView view) {
        this.view = view;
        this.repository = Injector.provideApiRepository();
    }

    void getSpeakerName (String idGroup) {
        view.showLoading(LOADING_MESSAGE);

        repository.getGroupById(idGroup, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                view.hideLoading();
                view.setSpeakerName(PRE_SPEAKER_NAME + group.getAdmin().getName());
            }

            @Override
            public void onError(String errorMessage) {
                getSpeakerName(idGroup);
            }
        });
    }

    void leftGroup (String idGroup) {
        view.showLoading(LOADING_MESSAGE);

        String userId = SharedPrefUtils.getStringSharedPref(Keys.ID_USER.getKey(), "");

        repository.leftGroup(userId, idGroup, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                view.hideLoading();
                view.moveToDashboard();
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showMessage(errorMessage);
            }
        });
    }
}
