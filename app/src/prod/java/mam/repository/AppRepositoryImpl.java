package mam.repository;


import java.util.List;

import javax.inject.Inject;

import florent37.github.com.mam.api.MamAPI;
import florent37.github.com.mam.common.StorageManager;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.model.AppResponse;
import florent37.github.com.mam.model.AppVersion;
import florent37.github.com.mam.model.AppsResponse;
import florent37.github.com.mam.model.AuthResponse;
import florent37.github.com.mam.repository.AppRepository;
import io.reactivex.Single;

public class AppRepositoryImpl implements AppRepository {

    private final MamAPI api;
    private final StorageManager storageManager;

    @Inject
    public AppRepositoryImpl(MamAPI api, StorageManager storageManager) {
        this.api = api;
        this.storageManager = storageManager;
    }

    @Override
    public Single<AuthResponse> auth(String login, String password) {
        return api.auth(login, password);
    }

    public Single<List<App>> applications() {
        return api.apps().map(AppsResponse::getApps);
    }


    public Single<List<AppVersion>> application(String name) {
        return api.app(name).map(AppResponse::getVersions);
    }
}
