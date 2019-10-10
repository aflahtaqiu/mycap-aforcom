package id.anforcom.mycap.module.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.anforcom.mycap.R;
import id.anforcom.mycap.module.main.MainActivity;
import id.anforcom.mycap.utils.CommunicationUtils;

public class SplashScreenActivity extends AppCompatActivity implements ISplashScreen{

    @BindView(R.id.layout_splash_screen)
    RelativeLayout relativeLayout;

    private static final Integer DELAY_MILLIS = 3000;

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        presenter = new SplashScreenPresenter(this);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        relativeLayout.startAnimation(animFadeIn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new SplashScreenRunnable(), DELAY_MILLIS);
    }

    @Override
    public void moveToMain() {
        CommunicationUtils.changeActivity(SplashScreenActivity.this, MainActivity.class);
    }

    private class SplashScreenRunnable implements Runnable {
        @Override
        public void run() {
            presenter.moveToMainActivity();
        }
    }
}
