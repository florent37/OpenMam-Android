package florent37.github.com.mam.ui.apps.recycler;

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
import florent37.github.com.mam.customviews.Icon;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.ui.apps.ClickListener;

/**
 * Created by florentchampigny on 20/06/2017.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    public static RecyclerView.ViewHolder build(ViewGroup parent, ClickListenerWrapper<ClickListener> clickListenerClickListenerWrapper) {
        return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_cell, parent, false), clickListenerClickListenerWrapper);
    }

    @BindView(R.id.icon)
    Icon icon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.category)
    TextView category;

    private App app;
    private ClickListenerWrapper<ClickListener> clickListenerWrapper;

    public AppViewHolder(View itemView, ClickListenerWrapper<ClickListener> clickListenerWrapper) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.clickListenerWrapper = clickListenerWrapper;
    }

    @OnClick(R.id.layout)
    public void onLayoutClicked(){
        clickListenerWrapper.getListener(listener ->
                listener.onAppClicked(app)
        );
    }

    public void bind(final App app){
        this.app = app;
        this.title.setText(app.getName());
        this.category.setText(app.getLastVersion());

        icon.loadText(app.getName());
        icon.loadUrl(app.getImage());
    }
}
