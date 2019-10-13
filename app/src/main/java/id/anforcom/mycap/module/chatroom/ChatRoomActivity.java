package id.anforcom.mycap.module.chatroom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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

    private ChatRoomPresenter presenter;

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognition;
    private DatabaseReference databaseReference;

    private String chatCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ButterKnife.bind(this);
        presenter = new ChatRoomPresenter(this);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new ChatRoomActivity.TranscibeRecognitionListener());

        getIntentData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Groups").child(chatCode);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    Log.e("data snapshot", chat.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getIntentData () {
        chatCode = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.CODE.getKey());

        tvCode.setText(chatCode);
    }

    private void promptSpeechInput() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);
        intentRecognition = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        intentRecognition.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
        speechRecognizer.startListening(intentRecognition);
    }

    @OnClick(R.id.btn_stop_chatroom)
    public void onStopBtnClicked () {

    }

    @OnClick(R.id.btn_microphone)
    public void onMicrophoneBtnClicked () {
        promptSpeechInput();
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
                hashMap.put("username", userName);
                hashMap.put("message", matches.get(0));

                databaseReference.child("groups").child(chatCode).child("chats").push().setValue(hashMap);

                Log.e("lele", "djakd");
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
