package id.anforcom.mycap.data.repository;

import java.util.List;

import id.anforcom.mycap.data.remote.ApiRemoteDataSource;
import id.anforcom.mycap.data.source.ApiDataSource;
import id.anforcom.mycap.model.Group;

/**
 * Created by aflah on 12/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ApiRepository implements ApiDataSource {

    private ApiRemoteDataSource remoteDataSource;

    private static ApiRepository repository;

    public ApiRepository(ApiRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static ApiRepository getInstance() {
        if (repository==null) {
            repository = new ApiRepository(ApiRemoteDataSource.getInstance());
        }
        return repository;
    }

    @Override
    public void getAllGroup(GroupListCallback callback) {
        remoteDataSource.getAllGroup(new GroupListCallback() {
            @Override
            public void onSuccess(List<Group> groups) {
                callback.onSuccess(groups);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getGroupById(String idGroup, GroupCallback callback) {
        remoteDataSource.getGroupById(idGroup, new GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                callback.onSuccess(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void createGroup(String groupCode, String adminName, GroupCallback callback) {
        remoteDataSource.createGroup(groupCode, adminName, new GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                callback.onSuccess(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void joinGroup(String userName, String groupCode, GroupCallback callback) {
        remoteDataSource.joinGroup(userName, groupCode, new GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                callback.onSuccess(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void leftGroup(String userId, String groupId, GroupCallback callback) {
        remoteDataSource.leftGroup(userId, groupId, new GroupCallback() {
            @Override
            public void onSuccess(Group group) {
                callback.onSuccess(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
}
