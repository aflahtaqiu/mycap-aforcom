package id.anforcom.mycap.module.conference_listener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

public class ConferenceListenerActivity extends BaseActivity implements IConferenceListenerView {

    @BindView(R.id.tv_conference_code)
    TextView tvConferenceCode;

    @BindView(R.id.tv_nama_speaker)
    TextView tvNamaSpeaker;

    @BindView(R.id.reyclerview_message_list)
    RecyclerView recyclerView;

    @BindView(R.id.btn_stop_conference)
    ImageView btnStopConference;

    private ConferenceListenerPresenter presenter;
    private ConferenceListenerAdapter adapter;

    private DatabaseReference databaseReference;

    private List<Chat> chats = new ArrayList<>();
    private String conferenceCode;
    private String idGroup;

    private static final String KEY_CHATS = "chats";
    private static final String KEY_GROUPS = "groups";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_listener);

        ButterKnife.bind(this);
        presenter = new ConferenceListenerPresenter(this);
        adapter = new ConferenceListenerAdapter(this.chats, this);

        getIntentData();
    }

    private void getIntentData () {
        conferenceCode = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.CODE.getKey());
        idGroup = getIntent().getBundleExtra(Keys.BUNDLE.getKey()).getString(Keys.ID_GROUP.getKey());

        presenter.getSpeakerName(idGroup);
        tvConferenceCode.setText(conferenceCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(KEY_GROUPS).child(conferenceCode).child(KEY_CHATS);

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

    @OnClick(R.id.btn_stop_conference)
    public void onStopBtnClicked () {
        presenter.leftGroup(idGroup);
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
                .setPositiveButton(R.string.ok, new ConferenceListenerActivity.OnOkClickListener())
                .setCancelable(false)
                .create();

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void moveToDashboard() {
        CommunicationUtils.changeActivity(ConferenceListenerActivity.this, DashboardActivity.class);
    }

    @Override
    public void setSpeakerName(String speakerName) {
        tvNamaSpeaker.setText(speakerName);
    }

    private static class OnOkClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }
}
