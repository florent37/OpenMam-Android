package florent37.github.com.mam.ui.apps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseFragment;
import florent37.github.com.mam.common.HeaderDecorator;
import florent37.github.com.mam.dagger.*;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.ui.versions.VersionsActivity;

public class AppsListFragment extends BaseFragment implements AppsPresenter.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    AppsPresenter presenter;

    @BindView(R.id.apps_header)
    View header;

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

        displayAsLines();
        presenter.start();

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int y = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                this.y -= dy;

                header.setTranslationY(y/2f);
            }
        });
    }

    private void displayAsList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        header.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.addItemDecoration(new HeaderDecorator(1, header.getHeight()));
                header.getViewTreeObserver().removeOnPreDrawListener(this);
                swipeRefreshLayout.setProgressViewOffset(false, header.getHeight(), header.getHeight() + 100);
                return false;
            }
        });
    }

    private void displayAsLines(){
        recyclerView.setAdapter(new AppsLinesAdapter().onClick(presenter::onAppClicked));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        header.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.addItemDecoration(new HeaderDecorator(1, header.getHeight()));
                swipeRefreshLayout.setProgressViewOffset(false, header.getHeight() - 100 , header.getHeight() + 100);
                header.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public static Fragment newInstance() {
        return new AppsListFragment();
    }

    @Override
    public void displayApps(List<App> apps) {
        swipeRefreshLayout.setRefreshing(false);
        ((AppsAdapter) recyclerView.getAdapter()).setItems(apps);
    }

    @Override
    public void displayApp(App app) {
        startActivity(VersionsActivity.newInstance(getContext(), app));
    }
}
