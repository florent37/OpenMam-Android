package florent37.github.com.mam.ui.apps;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import florent37.github.com.mam.common.AbstractPresenter;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.repository.AppRepository;

public class AppsPresenter extends AbstractPresenter<AppsPresenter.View> {

    private final AppRepository appRepository;
    private ArrayList<App> cachedApps;

    @Inject
    public AppsPresenter(AppRepository appRepository) {
        this.appRepository = appRepository;
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
