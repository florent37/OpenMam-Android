package florent37.github.com.mam.ui.versions;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import florent37.github.com.mam.common.AbstractPresenter;
import florent37.github.com.mam.common.DownloadManager;
import florent37.github.com.mam.common.InstallManager;
import florent37.github.com.mam.common.RxDownloader;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.model.AppVersion;
import florent37.github.com.mam.repository.AppRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VersionsPresenter extends AbstractPresenter<VersionsPresenter.View> {

    private final AppRepository appRepository;
    private final InstallManager installManager;
    private final DownloadManager downloadManager;
    @Nullable
    private List<AppVersion> appVersions;
    private App app;

    @Inject
    public VersionsPresenter(AppRepository appRepository, InstallManager installManager, DownloadManager downloadManager) {
        this.appRepository = appRepository;
        this.installManager = installManager;
        this.downloadManager = downloadManager;
    }

    public void init(App app){
        this.app = app;
    }

    public void start() {
        appRepository.application(app.getName())
                .compose(super.<List<AppVersion>>compose())
                .doOnSuccess((Consumer<List<AppVersion>>) versions -> appVersions = versions)
                .subscribe(
                        versions -> getView().displayAppVersions(versions),
                        throwable -> {
                });

        getView().displayApp(app.getName(), app.getLastVersion(), app.getLastCode());
    }

    public void onAppDownloadClicked(){
        if (appVersions != null && appVersions.size() > 0) {
            onVersionClicked(appVersions.get(0));
        }
    }

    public void onVersionClicked(AppVersion appVersion) {
        final String name = app.getName();
        final String url = appVersion.getUrl();

        call(Observable.just("download")
                .flatMap($ -> getView().requestPermissionWriteExternalStorage())
                .flatMap($ -> downloadManager.download(url, name + ".apk"))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::call)
                .subscribe(path -> {
                    installManager.installApk(name);
                    // Do what you want with downloaded path
                }, throwable -> {
                    throwable.printStackTrace();
                    // Handle download faile here
                }));
    }

    public interface View extends AbstractPresenter.View {
        void displayAppVersions(List<AppVersion> versions);

        void displayApp(String name, String version, String code);

        Observable<Boolean> requestPermissionWriteExternalStorage();
    }

}
