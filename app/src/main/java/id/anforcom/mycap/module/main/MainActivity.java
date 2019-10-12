package id.anforcom.mycap.module.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.anforcom.mycap.R;
import id.anforcom.mycap.base.BaseActivity;
import id.anforcom.mycap.module.dashboard.DashboardActivity;
import id.anforcom.mycap.utils.CommunicationUtils;

public class MainActivity extends BaseActivity implements IMainActivityView{

    @BindView(R.id.btn_start)
    Button btnStart;

    @BindView(R.id.btn_language_id)
    Button btnIndonesia;

    @BindView(R.id.btn_language_eng)
    Button btnEnglish;

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);
    }

    @OnClick(R.id.btn_start)
    public void onStartBtnClicked () {

        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        InstanceIdResult result = task.getResult();

                        if (!task.isSuccessful()) {
                            return;
                        }

                        if (result != null) {
                            Log.e("fcm token", result.getToken());
                            CommunicationUtils.changeActivity(MainActivity.this, DashboardActivity.class, false);
                        }
                    }
                });
    }

    @OnClick(R.id.btn_language_id)
    public void onBahasaBtnClicked () {
        presenter.setBahasa();
    }

    @OnClick(R.id.btn_language_eng)
    public void onEnglishBtnClicked () {
        presenter.setEnglish();
    }
}
