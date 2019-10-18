package id.anforcom.mycap.module.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.base.BaseActivity;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.module.chatroom.ChatRoomActivity;
import id.anforcom.mycap.module.conference_listener.ConferenceListenerActivity;
import id.anforcom.mycap.module.conference_speaker.ConferenceSpeakerActivity;
import id.anforcom.mycap.module.live_transcibe.LiveTranscibeActivity;
import id.anforcom.mycap.utils.CommunicationUtils;

public class DashboardActivity extends BaseActivity implements IDashboardView {

    @BindView(R.id.et_room_code)
    EditText etRoomCode;

    @BindView(R.id.btn_join)
    Button btnJoin;

    @BindView(R.id.cardview_group_chat)
    CardView cardViewGroupChat;

    @BindView(R.id.cardview_classroom)
    CardView cardViewClassRoom;

    @BindView(R.id.iv_microphone)
    ImageView ivMicrophone;

    private DashboardPresenter presenter;

    private final char CHAR_G = 'G';
    private final char CHAR_C = 'C';
    private static final String EMPTY_CODE = "Maaf, masukkan kode conference atau chat terlebih dahulu";
    private static final String WRONG_CODE_MESSAGE = "Maaf, code yang Anda masukkan salah";
    private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private final int CODE_LENGTH = 5;
    private final int ZERO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        presenter = new DashboardPresenter(this);
    }

    private String getRandomString(int randomStringLength) {
        StringBuilder stringBuilder = new StringBuilder();
        Random rnd = new Random();
        while (stringBuilder.length() < randomStringLength) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM_CHARS.length());
            stringBuilder.append(RANDOM_CHARS.charAt(index));
        }
        return stringBuilder.toString();
    }

    @OnClick(R.id.btn_join)
    public void onBtnJoinClicked () {

        if (!TextUtils.isEmpty(etRoomCode.getText().toString()))
            actionJoinBtnClicked(etRoomCode.getText().toString());
        else
            showMessage(EMPTY_CODE);
    }

    private void actionJoinBtnClicked (String code) {
        if (code.charAt(ZERO) == CHAR_G || code.charAt(ZERO) == CHAR_C) {
            presenter.joinGroup(code);
        } else {
            showMessage(WRONG_CODE_MESSAGE);
        }
    }

    @OnClick(R.id.cardview_group_chat)
    public void onCardViewGroupChatClicked () {
        String code = CHAR_G + getRandomString(CODE_LENGTH);
        presenter.createGroup(code);
    }

    @OnClick(R.id.cardview_classroom)
    public void onCardViewClassRoomClicked () {
        String code = CHAR_C + getRandomString(CODE_LENGTH);
        presenter.createGroup(code);
    }

    @OnClick(R.id.iv_microphone)
    public void onMicrophoneClicked () {
        moveLiveTranscibe();
    }

    @Override
    public void moveConferenceListener(String code, String idGroup) {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.CODE.getKey(), code);
        bundle.putString(Keys.ID_GROUP.getKey(), idGroup);
        CommunicationUtils.changeActivity(this, ConferenceListenerActivity.class, bundle,
                Keys.BUNDLE.getKey(), false);
    }

    @Override
    public void moveConferenceSpeaker(String code, String idGroup) {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.CODE.getKey(), code);
        bundle.putString(Keys.ID_GROUP.getKey(), idGroup);
        CommunicationUtils.changeActivity(this, ConferenceSpeakerActivity.class, bundle,
                Keys.BUNDLE.getKey(), false);
    }

    @Override
    public void moveChatRoom(String code, String idGroup) {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.CODE.getKey(), code);
        bundle.putString(Keys.ID_GROUP.getKey(), idGroup);
        CommunicationUtils.changeActivity(this, ChatRoomActivity.class, bundle,
                Keys.BUNDLE.getKey(), false);
    }

    @Override
    public void moveLiveTranscibe() {
        CommunicationUtils.changeActivity(DashboardActivity.this,
                LiveTranscibeActivity.class, false);
    }

    @Override
    public void showMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new OnOkClickListener())
                .setCancelable(false)
                .create();

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void showProgress(String message) {
        onShowLoading(message);
    }

    @Override
    public void hideProgress() {
        onHideLoading();
    }

    private static class OnOkClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }
}
