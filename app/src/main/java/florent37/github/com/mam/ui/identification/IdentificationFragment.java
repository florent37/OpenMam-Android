package florent37.github.com.mam.ui.identification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseFragment;
import florent37.github.com.mam.dagger.AppComponent;

/**
 * Created by florentchampigny on 19/07/2017.
 */

public class IdentificationFragment extends BaseFragment implements IdentificationPresenter.View {

    @Inject
    IdentificationPresenter presenter;

    public static Fragment newInstance() {
        return new IdentificationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent.from(getContext()).inject(this);
        presenter.bind(getLifecycle(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.apps_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
