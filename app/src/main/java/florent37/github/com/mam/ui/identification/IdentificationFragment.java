package florent37.github.com.mam.ui.identification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseFragment;
import florent37.github.com.mam.dagger.AppComponent;
import florent37.github.com.mam.ui.apps.AppsActivity;

public class IdentificationFragment extends BaseFragment implements IdentificationPresenter.View {

    @Inject
    IdentificationPresenter presenter;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.user)
    EditText user;

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
        return inflater.inflate(R.layout.fragment_identification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
        presenter.onLoginButtonClicked();
    }

    @OnEditorAction(R.id.password)
    public boolean onEditorAction(int actionId, KeyEvent key) {
        switch (actionId){
            case EditorInfo.IME_ACTION_GO:
            case EditorInfo.IME_ACTION_DONE:
            case EditorInfo.IME_ACTION_SEARCH:
            case EditorInfo.IME_ACTION_SEND:
                presenter.onLoginButtonClicked();
                break;
        }
        return true;
    }

    @Override
    public void launchNextScreen() {
        startActivity(AppsActivity.newInstance(getContext()));
        getActivity().finish();
    }

    @Override
    public String getLogin() {
        return user.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }
}
