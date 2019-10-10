package id.anforcom.mycap.module.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

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
        CommunicationUtils.changeActivity(MainActivity.this, DashboardActivity.class, false);
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
