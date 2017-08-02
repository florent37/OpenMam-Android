package florent37.github.com.mam.ui.identification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseActivity;
import florent37.github.com.mam.ui.apps.AppsActivity;
import florent37.github.com.mam.ui.apps.AppsListFragment;

/**
 * Created by florentchampigny on 19/07/2017.
 */

public class IdentificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        displayApps();
    }

    public void displayApps() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, IdentificationFragment.newInstance())
                .commitAllowingStateLoss();
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, IdentificationActivity.class);
    }
}
