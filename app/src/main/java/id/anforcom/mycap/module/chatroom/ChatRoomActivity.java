package id.anforcom.mycap.module.chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.model.Keys;

public class ChatRoomActivity extends AppCompatActivity implements IChatRoomView {

    @BindView(R.id.tv_chatroom_code)
    TextView tvCode;

    @BindView(R.id.btn_stop_chatroom)
    ImageView ivStopChatRoom;

    private ChatRoomPresenter presenter;

    private String chatCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ButterKnife.bind(this);
        presenter = new ChatRoomPresenter(this);

        getIntentData();
    }

    private void getIntentData () {
        chatCode = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.CODE.getKey());

        tvCode.setText(chatCode);
    }

    @OnClick(R.id.btn_stop_chatroom)
    public void onStopBtnClicked () {

    }
}
