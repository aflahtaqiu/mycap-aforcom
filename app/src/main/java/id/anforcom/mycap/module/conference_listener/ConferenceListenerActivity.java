package id.anforcom.mycap.module.conference_listener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import butterknife.ButterKnife;
import id.anforcom.mycap.R;

public class ConferenceListenerActivity extends AppCompatActivity implements IConferenceListenerView {

    private ConferenceListenerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_listener);

        ButterKnife.bind(this);
        presenter = new ConferenceListenerPresenter(this);
    }
}
