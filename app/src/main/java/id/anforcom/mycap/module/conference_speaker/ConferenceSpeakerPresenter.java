package id.anforcom.mycap.module.conference_speaker;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import id.anforcom.mycap.data.repository.ApiRepository;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.di.Injector;
import id.anforcom.mycap.model.Group;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.model.User;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 10/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class ConferenceSpeakerPresenter {

    private IConferenceSpeakerView view;
    private ApiRepository repository;

    private static final String LOADING_MESSAGE = "Loading...";
    private static final String ERROR_TIMEOUT = "timeout";

    ConferenceSpeakerPresenter(IConferenceSpeakerView view) {
        this.view = view;
        this.repository = Injector.provideApiRepository();
    }

    void updateListeners(String idGroup) {
        view.showLoading(LOADING_MESSAGE);
        String userName = SharedPrefUtils.getStringSharedPref(Keys.NAMA_USER.getKey(), "");

        repository.getGroupById(idGroup, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                List<User> listeners = new ArrayList<>();

                for (User user : group.getUsers()){
                    if (!TextUtils.equals(userName, user.getName()))
                        listeners.add(user);
                }

                view.setListenerData(listeners);
                view.hideLoading();
            }

            @Override
            public void onError(String errorMessage) {
                if (TextUtils.equals(errorMessage, ERROR_TIMEOUT))
                    updateListeners(idGroup);
                else {
                    view.hideLoading();
                    view.showMessage(errorMessage);
                }
            }
        });
    }

    void leftGroup (String groupId) {
        view.showMessage(LOADING_MESSAGE);

        String userId = SharedPrefUtils.getStringSharedPref(Keys.ID_USER.getKey(), "");

        repository.leftGroup(userId, groupId, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                view.hideLoading();
                view.moveToDashboard();
            }

            @Override
            public void onError(String errorMessage) {
                if (TextUtils.equals(errorMessage, ERROR_TIMEOUT))
                    leftGroup(groupId);
                else {
                    view.hideLoading();
                    view.showMessage(errorMessage);
                }
            }
        });
    }
}
