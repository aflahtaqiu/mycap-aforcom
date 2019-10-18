package id.anforcom.mycap.module.live_transcibe;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.base.BaseActivity;
import id.anforcom.mycap.model.Keys;
import id.anforcom.mycap.utils.SharedPrefUtils;

public class LiveTranscibeActivity extends BaseActivity {

    @BindView(R.id.btn_stop_live_transcibe)
    ImageView btnStop;

    @BindView(R.id.tv_live_transcibe)
    TextView textView;

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognition;

    private static boolean IS_SPEECH_RECOGNIZING = false;

    private static String STRING_RESULT;
    private static String SELECTED_LANGUAGE;

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
        STRING_RESULT = "";
        SELECTED_LANGUAGE = SharedPrefUtils.getStringSharedPref(Keys.PILIHAN_BAHASA.getKey(), Keys.BAHASA_ID.getKey());

        promptSpeechInput();
    }

    @OnClick(R.id.btn_stop_live_transcibe)
    public void onBtnStopClicked () {
        speechRecognizer.stopListening();
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

    private class TranscibeRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {
            IS_SPEECH_RECOGNIZING = true;
            btnStop.setBackground(getResources().getDrawable(R.drawable.ic_cancel));
        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
            IS_SPEECH_RECOGNIZING = false;
            btnStop.setBackground(getResources().getDrawable(R.drawable.ic_microphone));
        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {

            List<String> listOfWords = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            assert listOfWords != null;

            STRING_RESULT += listOfWords.get(0);
            textView.setText(STRING_RESULT);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            speechRecognizer.stopListening();
            speechRecognizer.setRecognitionListener(this);
            speechRecognizer.startListening(intentRecognition);
        }

        @Override
        public void onPartialResults(Bundle bundle) {

            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String b = "";
            if (results != null) {
                if (results.size() > 6) {
                    results.clear();
                }
                for (String p : results) {
                    b += p;
                }
            }

            textView.setText(STRING_RESULT + b);

//            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//            if (matches != null && matches.size() != 0) {
//                textView.setText(matches.get(0));
//            }else{
//                Toast.makeText(LiveTranscibeActivity.this, "Tidak dapat mendengar kata-kata", Toast.LENGTH_LONG).show();
//            }
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    }

    @OnClick(R.id.btn_stop_live_transcibe)
    public void onStopClicked () {
        if (!IS_SPEECH_RECOGNIZING)
            promptSpeechInput();
    }
}