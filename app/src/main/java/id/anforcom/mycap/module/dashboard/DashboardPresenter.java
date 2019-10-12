package id.anforcom.mycap.module.dashboard;

import id.anforcom.mycap.data.repository.ApiRepository;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.di.Injector;
import id.anforcom.mycap.model.Group;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

/**
 * Created by aflah on 04/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class DashboardPresenter {

    private IDashboardView view;
    private ApiRepository repository;

    private final char CHAR_G = 'G';
    private final char CHAR_C = 'C';
    private final int ZERO = 0;

    public DashboardPresenter(IDashboardView view) {
        this.view = view;
        this.repository = Injector.provideApiRepository();
    }

    void createGroup (String groupCode) {
        String userName = SharedPrefUtils.getStringSharedPref(Keys.NAMA_USER.getKey(), "");

        repository.createGroup(groupCode, userName, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                if (groupCode.charAt(ZERO) == CHAR_C)
                    view.moveConferenceSpeaker(group.getGroupCode());
                else
                    view.moveChatRoom(group.getGroupCode());
            }

            @Override
            public void onError(String errorMessage) {
                view.showMessage(errorMessage);
            }
        });
    }

    void joinGroup (String groupCode) {
        String userName = SharedPrefUtils.getStringSharedPref(Keys.NAMA_USER.getKey(), "");

        repository.joinGroup(userName, groupCode, new ApiDataSource.GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                if (groupCode.charAt(ZERO) == CHAR_C)
                    view.moveConferenceSpeaker(group.getGroupCode());
                else
                    view.moveChatRoom(group.getGroupCode());
            }

            @Override
            public void onError(String errorMessage) {
                view.showMessage(errorMessage);
            }
        });
    }
}
