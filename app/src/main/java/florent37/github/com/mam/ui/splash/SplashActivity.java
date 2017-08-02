package florent37.github.com.mam.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseActivity;
import florent37.github.com.mam.ui.apps.AppsActivity;
import florent37.github.com.mam.ui.identification.IdentificationActivity;
import io.reactivex.Observable;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .doOnSubscribe(this::call)
                .subscribe($ -> launchNextScreen());
    }

    protected void launchNextScreen(){
        overridePendingTransition(0, 0);
        startActivity(IdentificationActivity.newInstance(SplashActivity.this));
        finish();
    }
}
