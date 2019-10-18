package id.anforcom.mycap.module.chatroom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.base.BaseActivity;
import id.anforcom.mycap.model.Chat;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.module.dashboard.DashboardActivity;
import id.anforcom.mycap.utils.CommunicationUtils;
import id.anforcom.mycap.utils.SharedPrefUtils;

public class ChatRoomActivity extends BaseActivity implements IChatRoomView {

    @BindView(R.id.tv_chatroom_code)
    TextView tvCode;

    @BindView(R.id.btn_stop_chatroom)
    ImageView ivStopChatRoom;

    @BindView(R.id.btn_microphone)
    ImageView ivMicrophone;

    @BindView(R.id.reyclerview_message_list)
    RecyclerView recyclerViewMessage;

    private ChatRoomPresenter presenter;
    private ChatAdapter adapter;

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognition;
    private DatabaseReference databaseReference;

    private String chatCode;
    private String idGroup;
    private List<Chat> chats = new ArrayList<>();

    private static String SELECTED_LANGUAGE;

    private static final String KEY_CHATS = "chats";
    private static final String KEY_GROUPS = "groups";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ButterKnife.bind(this);
        presenter = new ChatRoomPresenter(this);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new ChatRoomActivity.TranscibeRecognitionListener());

        getIntentData();

        adapter = new ChatAdapter(chats, this);

        recyclerViewMessage.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessage.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SELECTED_LANGUAGE = SharedPrefUtils.getStringSharedPref(Keys.PILIHAN_BAHASA.getKey(), Keys.BAHASA_ID.getKey());

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(KEY_GROUPS).child(chatCode).child(KEY_CHATS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    chats.add(chat);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getIntentData () {
        chatCode = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.CODE.getKey());
        idGroup = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.ID_GROUP.getKey());

        tvCode.setText(chatCode);
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

    @OnClick(R.id.btn_stop_chatroom)
    public void onStopBtnClicked () {
        presenter.leftGroup(idGroup);
    }

    @OnClick(R.id.btn_microphone)
    public void onMicrophoneBtnClicked () {
        promptSpeechInput();
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
                .setPositiveButton(R.string.ok, new OnOkClickListener())
                .setCancelable(false)
                .create();

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void moveToDashboard() {
        CommunicationUtils.changeActivity(ChatRoomActivity.this, DashboardActivity.class);
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

                databaseReference.child(KEY_GROUPS).child(chatCode).child(KEY_CHATS).push().setValue(hashMap);
            }else{
                Toast.makeText(ChatRoomActivity.this, "Tidak dapat mendengar kata-kata", Toast.LENGTH_LONG).show();
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
