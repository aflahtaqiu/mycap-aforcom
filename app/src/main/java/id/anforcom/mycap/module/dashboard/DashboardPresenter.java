package id.anforcom.mycap.module.dashboard;

import android.text.TextUtils;

import id.anforcom.mycap.data.repository.ApiRepository;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.di.Injector;
import id.anforcom.mycap.model.Group;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.model.User;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 04/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class DashboardPresenter {

    private IDashboardView view;
    private ApiRepository repository;

    private final char CHAR_G = 'G';
    private final char CHAR_C = 'C';
    private final int ZERO = 0;
    private final String LOADING_MESSAGE = "Loading...";

    DashboardPresenter(IDashboardView view) {
        this.view = view;
        this.repository = Injector.provideApiRepository();
    }

    void createGroup (String groupCode) {
        view.showProgress(LOADING_MESSAGE);
        String userName = SharedPrefUtils.getStringSharedPref(Keys.NAMA_USER.getKey(), "");

        repository.createGroup(groupCode, userName, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {

                for (User user : group.getUsers()) {
                    if (TextUtils.equals(userName, user.getName())) {
                        SharedPrefUtils.setStringSharedPref(Keys.ID_USER.getKey(), user.getId());
                    }
                }

                view.hideProgress();

                if (groupCode.charAt(ZERO) == CHAR_C)
                    view.moveConferenceSpeaker(group.getGroupCode(), group.getId());
                else
                    view.moveChatRoom(group.getGroupCode(), group.getId());
            }

            @Override
            public void onError(String errorMessage) {
                view.hideProgress();
                view.showMessage(errorMessage);
            }
        });
    }

    void joinGroup (String groupCode) {
        view.showProgress(LOADING_MESSAGE);
        String userName = SharedPrefUtils.getStringSharedPref(Keys.NAMA_USER.getKey(), "");

        repository.joinGroup(userName, groupCode, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {

                for (User user : group.getUsers()) {
                    if (TextUtils.equals(userName, user.getName())) {
                        SharedPrefUtils.setStringSharedPref(Keys.ID_USER.getKey(), user.getId());
                    }
                }

                view.hideProgress();

                if (groupCode.charAt(ZERO) == CHAR_C)
                    view.moveConferenceListener(group.getGroupCode(), group.getId());
                else
                    view.moveChatRoom(group.getGroupCode(), group.getId());
            }

            @Override
            public void onError(String errorMessage) {
                view.hideProgress();
                view.showMessage(errorMessage);
            }
        });
    }
}
