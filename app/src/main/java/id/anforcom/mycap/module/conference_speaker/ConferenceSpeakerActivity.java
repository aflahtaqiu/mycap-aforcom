package id.anforcom.mycap.module.conference_speaker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.base.BaseActivity;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.model.User;
import id.anforcom.mycap.module.dashboard.DashboardActivity;
import id.anforcom.mycap.utils.CommunicationUtils;
import id.anforcom.mycap.utils.SharedPrefUtils;

public class ConferenceSpeakerActivity extends BaseActivity implements IConferenceSpeakerView {

    @BindView(R.id.tv_conference_code)
    TextView tvCode;

    @BindView(R.id.btn_stop_conference_speaker)
    ImageView ivStopChatRoom;

    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;

    @BindView(R.id.rv_conference_listeners)
    RecyclerView recyclerView;

    private ConferenceSpeakerPresenter presenter;
    private ConferenceSpeakerAdapter adapter;

    private List<User> userList = new ArrayList<>();

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognition;

    private String conferenceCode;
    private String idGroup;

    private static String SELECTED_LANGUAGE;

    private static final int ITEM_SPAN_COUNT = 3;
    private static final String KEY_CHATS = "chats";
    private static final String KEY_GROUPS = "groups";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_speaker);

        ButterKnife.bind(this);
        presenter = new ConferenceSpeakerPresenter(this);
        adapter = new ConferenceSpeakerAdapter(this.userList, this);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new ConferenceSpeakerActivity.TranscibeRecognitionListener());

        getIntentData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, ITEM_SPAN_COUNT));

        SELECTED_LANGUAGE = SharedPrefUtils.getStringSharedPref(Keys.PILIHAN_BAHASA.getKey(), Keys.BAHASA_ID.getKey());
    }

    private void promptSpeechInput() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);

        intentRecognition = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intentRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE, SELECTED_LANGUAGE);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, SELECTED_LANGUAGE);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, SELECTED_LANGUAGE);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, SELECTED_LANGUAGE);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intentRecognition.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);

        speechRecognizer.startListening(intentRecognition);
    }

    private void getIntentData () {
        conferenceCode = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.CODE.getKey());
        idGroup = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.ID_GROUP.getKey());

        tvCode.setText(conferenceCode);
    }

    @OnClick(R.id.btn_microphone)
    public void onMicrophoneBtnClicked () {
        promptSpeechInput();
    }

    @OnClick(R.id.btn_stop_conference_speaker)
    public void onBtnStopClicked () {
        presenter.leftGroup(idGroup);
    }

    @OnClick(R.id.iv_refresh)
    public void onRefreshClicked () {
        presenter.updateListeners(idGroup);
    }

    @Override
    public void setListenerData(List<User> users) {
        this.userList.clear();
        this.userList.addAll(users);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void moveToDashboard() {
        CommunicationUtils.changeActivity(ConferenceSpeakerActivity.this, DashboardActivity.class);
    }

    @Override
    public void showLoading(String message) {
        onShowLoading(message);
    }

    @Override
    public void hideLoading() {
        onHideLoading();
    }

    @Override
    public void showMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new ConferenceSpeakerActivity.OnOkClickListener())
                .setCancelable(false)
                .create();

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private static class OnOkClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    private class TranscibeRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {
            String userName = SharedPrefUtils.getStringSharedPref(Keys.NAMA_USER.getKey(), "");
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && matches.size() != 0) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(KEY_USERNAME, userName);
                hashMap.put(KEY_MESSAGE, matches.get(0));

                databaseReference.child(KEY_GROUPS).child(conferenceCode).child(KEY_CHATS).push().setValue(hashMap);
            }else{
                Toast.makeText(ConferenceSpeakerActivity.this, "Tidak dapat mendengar kata-kata", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    }
}
