package florent37.github.com.mam.ui.identification;

import javax.inject.Inject;

import florent37.github.com.mam.common.AbstractPresenter;
import florent37.github.com.mam.common.StorageManager;
import florent37.github.com.mam.model.AuthResponse;
import florent37.github.com.mam.repository.AppRepository;
import florent37.github.com.mam.repository.Repository;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by florentchampigny on 19/07/2017.
 */

public class IdentificationPresenter extends AbstractPresenter<IdentificationPresenter.View> {

    private final StorageManager storageManager;
    private final AppRepository repository;

    @Inject
    public IdentificationPresenter(StorageManager storageManager, AppRepository repository) {
        this.storageManager = storageManager;
        this.repository = repository;
    }

    public void start() {

    }

    public void onLoginButtonClicked() {
        final String login = getView().getLogin();
        final String password = getView().getPassword();
        repository.auth(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::call)
                .subscribe(authResponse -> {
                    storageManager.saveToken(authResponse.getToken());
                    getView().launchNextScreen();
                });
    }

    public interface View extends AbstractPresenter.View {
        void launchNextScreen();
        String getLogin();
        String getPassword();
    }
}
