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
import id.anforcom.mycap.model.Keys;
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

    private static String EMPTY_NAME_FIELD = "Pastikan sudah memilih bahasa dan mengisi nama";
    private static String LOADING = "Loading ...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!presenter.isLanguageSelectedEmpty()) {
            if (TextUtils.equals(presenter.getSelectedLanguage(), Keys.EN_US.getKey()))
                btnEnglish.setBackground(getResources().getDrawable(R.drawable.btn_grey));
            else
                btnIndonesia.setBackground(getResources().getDrawable(R.drawable.btn_grey));
        }
    }

    private boolean isValid () {
        return !TextUtils.isEmpty(etNamaUser.getText().toString()) && !presenter.isLanguageSelectedEmpty();
    }

    @OnClick(R.id.btn_start)
    public void onStartBtnClicked () {

        if (isValid()) {
            showLoading(LOADING);
            presenter.saveDataToSharedPreference(etNamaUser.getText().toString());
        } else {
            showEmptyFieldAlert(EMPTY_NAME_FIELD);
        }
    }

    @OnClick(R.id.btn_language_id)
    public void onBahasaBtnClicked () {
        btnIndonesia.setBackground(getResources().getDrawable(R.drawable.btn_grey));
        btnEnglish.setBackground(getResources().getDrawable(R.drawable.search_box));
        presenter.setBahasa();
    }

    @OnClick(R.id.btn_language_eng)
    public void onEnglishBtnClicked () {
        btnEnglish.setBackground(getResources().getDrawable(R.drawable.btn_grey));
        btnIndonesia.setBackground(getResources().getDrawable(R.drawable.search_box));
        presenter.setEnglish();
    }

    @Override
    public void moveToDashboard() {
        CommunicationUtils.changeActivity(MainActivity.this, DashboardActivity.class);
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

    }

    private class OnOkClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }
}
