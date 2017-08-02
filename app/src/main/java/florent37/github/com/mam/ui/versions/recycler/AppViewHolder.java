package florent37.github.com.mam.ui.versions.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.ClickListenerWrapper;
import florent37.github.com.mam.model.AppVersion;

/**
 * Created by florentchampigny on 20/06/2017.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment)
    TextView comment;

    @BindView(R.id.date)
    TextView date;

    private AppVersion appVersion;
    private ClickListenerWrapper<VersionsAdapter.ClickListener> clickListenerWrapper;

    public AppViewHolder(View itemView, ClickListenerWrapper<VersionsAdapter.ClickListener> clickListenerWrapper) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.clickListenerWrapper = clickListenerWrapper;
    }

    public static RecyclerView.ViewHolder build(ViewGroup parent, ClickListenerWrapper<VersionsAdapter.ClickListener> clickListenerClickListenerWrapper) {
        return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.appversion_first_cell, parent, false), clickListenerClickListenerWrapper);
    }

    public void bind(final AppVersion appVersion) {
        this.appVersion = appVersion;

        this.comment.setText(appVersion.getComment());
        this.date.setText(appVersion.getDate());
    }
}
