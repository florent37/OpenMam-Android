package florent37.github.com.mam.ui.apps;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.ClickListenerWrapper;
import florent37.github.com.mam.model.App;

/**
 * Created by florentchampigny on 20/06/2017.
 */

public class AppLineViewHolder extends RecyclerView.ViewHolder {

    public static RecyclerView.ViewHolder build(ViewGroup parent, ClickListenerWrapper<ClickListener> clickListenerClickListenerWrapper) {
        return new AppLineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_line, parent, false), clickListenerClickListenerWrapper);
    }

    private final List<AppViewHolder> appViewHolder = new ArrayList<>();

    @BindViews({ R.id.app0, R.id.app1, R.id.app2 })
    List<View> appViews;

    @BindView(R.id.categoryTitle)
    TextView categoryTitle;

    public AppLineViewHolder(View itemView, ClickListenerWrapper<ClickListener> clickListenerWrapper) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        for (View appView : appViews) {
            appViewHolder.add(new AppViewHolder(appView, clickListenerWrapper));
        }
    }

    @OnClick(R.id.layout)
    public void onLayoutClicked(){
    }

    public void bind(final List<App> app){
        for (int i = 0; i < appViewHolder.size(); i++) {
            final App appl = getApp(app, i);
            final View view = appViews.get(i);
            if (appl != null) {
                view.setClickable(true);
                view.setVisibility(View.VISIBLE);
                appViewHolder.get(i).bind(appl);
            } else {
                view.setClickable(false);
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    private App getApp(List<App> apps, int index){
        if(index < apps.size()){
            return apps.get(index);
        } else {
            return null;
        }
    }

}
