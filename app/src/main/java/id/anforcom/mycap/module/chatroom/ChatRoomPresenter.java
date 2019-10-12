package id.anforcom.mycap.module.chatroom;

import id.anforcom.mycap.data.repository.ApiRepository;
import id.anforcom.mycap.di.Injector;

/**
 * Created by aflah on 08/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ChatRoomPresenter {

    private IChatRoomView view;
    private ApiRepository repository;

    public ChatRoomPresenter(IChatRoomView view) {
        this.view = view;
        this.repository = Injector.provideApiRepository();
    }
}
