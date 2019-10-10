package id.anforcom.mycap.module.live_transcibe;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.base.BaseActivity;

public class LiveTranscibeActivity extends BaseActivity implements ILiveTranscibeActivityView {

    @BindView(R.id.btn_stop_live_transcibe)
    ImageView btnStop;

    @BindView(R.id.tv_live_transcibe)
    TextView textView;

    private LiveTranscibeActivityPresenter presenter;

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_transcibe);
        ButterKnife.bind(this);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new TranscibeRecognitionListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
        promptSpeechInput();
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @OnClick(R.id.btn_stop_live_transcibe)
    public void onBtnStopClicked () {
        speechRecognizer.stopListening();
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
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && matches.size() != 0) {
                textView.append(matches.get(0) + ". ");
            }else{
                Toast.makeText(LiveTranscibeActivity.this, "Tidak dapat mendengar kata-kata", Toast.LENGTH_LONG).show();
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