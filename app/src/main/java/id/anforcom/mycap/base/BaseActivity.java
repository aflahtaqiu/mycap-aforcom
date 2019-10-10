package id.anforcom.mycap.base;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by aflah on 09/08/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    protected void onShowLoading(String message) {
        progressDialog.setMessage(message);

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void onHideLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
