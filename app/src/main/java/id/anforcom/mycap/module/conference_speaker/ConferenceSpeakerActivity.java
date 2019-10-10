package id.anforcom.mycap.module.conference_speaker;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.model.User;

public class ConferenceSpeakerActivity extends AppCompatActivity implements IConferenceSpeakerView {

    @BindView(R.id.tv_conference_code)
    TextView tvCode;

    @BindView(R.id.btn_stop_conference_speaker)
    ImageView ivStopChatRoom;

    private ConferenceSpeakerPresenter presenter;

    private List<User> userList = new ArrayList<>();
    private String conferenceCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_speaker);

        ButterKnife.bind(this);
        presenter = new ConferenceSpeakerPresenter(this);

        getIntentData();
    }

    private void getIntentData () {
        conferenceCode = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.CODE.getKey());

        tvCode.setText(conferenceCode);
    }

    @OnClick(R.id.btn_stop_conference_speaker)
    public void onBtnStopClicked () {

    }
}
