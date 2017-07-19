package florent37.github.com.mam.ui.apps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseActivity;
import florent37.github.com.mam.ui.splash.SplashActivity;

public class AppsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayApps();
    }

    public void displayApps() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, AppsListFragment.newInstance())
                .commitAllowingStateLoss();
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, AppsActivity.class);
    }
}
