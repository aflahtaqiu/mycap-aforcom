package id.anforcom.mycap.module.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

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

    @BindView(R.id.et_name_user)
    EditText etNamaUser;

    private MainActivityPresenter presenter;

    private static String EMPTY_NAME_FIELD = "Harus mengisi nama";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);
    }

    @OnClick(R.id.btn_start)
    public void onStartBtnClicked () {

        if (!TextUtils.isEmpty(etNamaUser.getText().toString())) {
            presenter.saveDataToSharedPreference(etNamaUser.getText().toString());
        } else {
            showEmptyFieldAlert(EMPTY_NAME_FIELD);
        }
    }

    @OnClick(R.id.btn_language_id)
    public void onBahasaBtnClicked () {
        presenter.setBahasa();
    }

    @OnClick(R.id.btn_language_eng)
    public void onEnglishBtnClicked () {
        presenter.setEnglish();
    }

    @Override
    public void moveToDashboard() {
        CommunicationUtils.changeActivity(MainActivity.this, DashboardActivity.class, false);
    }

    @Override
    public void showEmptyFieldAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new MainActivity.OnOkClickListener())
                .setCancelable(false)
                .create();

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private class OnOkClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }
}
