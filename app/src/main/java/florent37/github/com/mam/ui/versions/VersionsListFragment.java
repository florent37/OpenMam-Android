package florent37.github.com.mam.ui.versions;

import android.Manifest;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.BaseFragment;
import florent37.github.com.mam.common.HeaderDecorator;
import florent37.github.com.mam.dagger.AppComponent;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.model.AppVersion;
import io.reactivex.Observable;

public class VersionsListFragment extends BaseFragment implements VersionsPresenter.View {

    static final String APPLICATION = "APPLICATION";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.appversions_header)
    View header;

    @BindView(R.id.appName)
    TextView appName;

    @BindView(R.id.appVersion)
    TextView appVersion;

    @BindView(R.id.appCode)
    TextView appCode;

    @BindView(R.id.appLayout)
    View appLayout;

    @BindView(R.id.appIcon)
    TextView appIcon;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    VersionsPresenter presenter;

    public static VersionsListFragment newInstance(App app) {
        final Bundle args = new Bundle();
        args.putSerializable(APPLICATION, app);
        VersionsListFragment fragment = new VersionsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static float dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent.from(getContext()).inject(this);
        presenter.bind(getLifecycle(), this);
        presenter.init((App) getArguments().getSerializable(APPLICATION));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.appversions_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupActionbar();

        recyclerView.setAdapter(new VersionsAdapter().onClick(presenter::onVersionClicked));

        setupRecyclerView();

        presenter.start();
    }

    private void setupRecyclerView() {
        final int totalSpan = 3;
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), totalSpan);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position) {
                    case 0:
                        return totalSpan;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        header.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.addItemDecoration(new HeaderDecorator(1, header.getHeight()));
                header.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

        final float statusBar = dpToPx(getContext(), 28);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int y = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                this.y -= dy;

                if (appLayout.getTop() + y >= statusBar) {
                    header.setTranslationY(y);

                    if (appLayout.getTop() != 0) {
                        final float percent =  y / appLayout.getTop();

                        final float translation = -1 * dpToPx(getContext(), 27) * percent;
                        appVersion.setTranslationY(translation);
                        appCode.setTranslationY(translation);
                        appIcon.setTranslationY(translation);
                        appName.setTranslationY(translation);
                    }
                } else {
                    header.setTranslationY(-appLayout.getTop() + statusBar);
                }
            }
        });
    }

    private void setupActionbar() {
        AppCompatActivity appCompatActivity = ((AppCompatActivity) getActivity());
        appCompatActivity.setSupportActionBar(toolbar);

        final ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void displayAppVersions(List<AppVersion> versions) {
        ((VersionsAdapter) recyclerView.getAdapter()).setItems(versions);
    }

    @Override
    public void displayApp(AppVersion appVersion){
        ((VersionsAdapter) recyclerView.getAdapter()).setHeaderObject(appVersion);
    }

    @Override
    public void displayApp(String name, String code, String version) {
        appName.setText(name);
        appVersion.setText("Version " + version);
        appCode.setText("(" + code + ")");
        appIcon.setText(String.valueOf(name.charAt(0)));
    }

    @OnClick(R.id.install)
    public void onInstallClicked(){
        presenter.onAppDownloadClicked();
    }

    @Override
    public Observable<Boolean> requestPermissionWriteExternalStorage() {
        return new RxPermissions(getActivity())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

}
