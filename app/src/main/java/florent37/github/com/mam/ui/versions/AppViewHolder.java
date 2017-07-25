package florent37.github.com.mam.ui.versions;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.ClickListenerWrapper;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.model.AppVersion;

/**
 * Created by florentchampigny on 20/06/2017.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment)
    TextView comment;

    @BindView(R.id.date)
    TextView date;

    private App app;
    private ClickListenerWrapper<VersionsAdapter.ClickListener> clickListenerWrapper;

    public AppViewHolder(View itemView, ClickListenerWrapper<VersionsAdapter.ClickListener> clickListenerWrapper) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.clickListenerWrapper = clickListenerWrapper;
    }

    public static RecyclerView.ViewHolder build(ViewGroup parent, ClickListenerWrapper<VersionsAdapter.ClickListener> clickListenerClickListenerWrapper) {
        return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.appversion_first_cell, parent, false), clickListenerClickListenerWrapper);
    }

    public void bind(final App app) {
        this.app = app;

       // this.date.setText(appVersion.getDate());
       // this.code.setText(String.format("(%s)", appVersion.getCode()));
       // this.version.setText(appVersion.getVersion());
    }
}
