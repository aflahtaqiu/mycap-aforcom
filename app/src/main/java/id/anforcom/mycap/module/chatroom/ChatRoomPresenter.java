package id.anforcom.mycap.module.chatroom;

import android.telecom.Call;
import android.text.TextUtils;

import id.anforcom.mycap.data.repository.ApiRepository;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.di.Injector;
import id.anforcom.mycap.model.Group;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 08/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class ChatRoomPresenter {

    private IChatRoomView view;
    private ApiRepository repository;

    private static final String LOADING_MESSAGE = "Loading...";
    private static final String ERROR_TIMEOUT = "timeout";

    ChatRoomPresenter(IChatRoomView view) {
        this.view = view;
        this.repository = Injector.provideApiRepository();
    }

    void leftGroup (String groupId) {
        view.showLoading(LOADING_MESSAGE);

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
