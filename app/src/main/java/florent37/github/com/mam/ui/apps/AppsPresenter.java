package florent37.github.com.mam.ui.apps;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import florent37.github.com.mam.bus.MainBus;
import florent37.github.com.mam.common.AbstractPresenter;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.repository.AppRepository;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

public class AppsPresenter extends AbstractPresenter<AppsPresenter.View> {

    private final AppRepository appRepository;
    private final MainBus mainBus;
    private ArrayList<App> cachedApps;

    @Inject
    public AppsPresenter(AppRepository appRepository, MainBus mainBus) {
        this.appRepository = appRepository;
        this.mainBus = mainBus;
    }

    public void start() {
        appRepository.applications()
                .compose(super.<List<App>>compose())
                .doOnSuccess(apps -> cachedApps = new ArrayList<>(apps))
                .subscribe(
                        apps ->
                                getView().displayApps(apps),
                        throwable -> {
                            throwable.printStackTrace();
                        });
    }

    public void onAppClicked(App app) {
        getView().displayApp(app);
    }

    public void refresh() {
        start();
    }

    public void onSearch(String query) {
        if (cachedApps != null) {
            if(query == null || query.trim().isEmpty()){
                getView().displayApps(cachedApps);
            } else {
                final List<App> filtered = new ArrayList<>();
                for (App cachedApp : cachedApps) {
                    if (cachedApp.getName().toLowerCase().contains(query.toLowerCase())) {
                        filtered.add(cachedApp);
                    }
                }
                getView().displayApps(filtered);
            }
        }
    }

    public interface View extends AbstractPresenter.View {
        void displayApps(List<App> apps);

        void displayApp(App app);
    }

}
