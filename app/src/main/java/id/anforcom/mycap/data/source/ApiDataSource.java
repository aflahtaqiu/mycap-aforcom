package id.anforcom.mycap.data.source;

import java.util.List;

import id.anforcom.mycap.model.Group;

/**
 * Created by aflah on 12/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public interface ApiDataSource {

    void getAllGroup (GroupListCallback callback);
    void getGroupById (String idGroup, GroupCallback callback);
    void createGroup (String groupCode, String adminName, GroupCallback callback);
    void joinGroup (String userName, String groupCode, GroupCallback callback);
    void leftGroup (String userId, String groupId, GroupCallback callback);

    interface GroupListCallback {
        void onSuccess(List<Group> groups);
        void onError(String errorMessage);
    }

    interface GroupCallback {
        void onSuccess(Group group);
        void onError(String errorMessage);
    }
}
