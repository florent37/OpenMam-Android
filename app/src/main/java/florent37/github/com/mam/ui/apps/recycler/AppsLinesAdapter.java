package florent37.github.com.mam.ui.apps.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import florent37.github.com.mam.common.ClickListenerWrapper;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.ui.apps.ClickListener;

public class AppsLinesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppsAdapter<AppsLinesAdapter>{

    private static final float NB_ITEMS_PER_LINE = 3f;

    private List<App> appList = new ArrayList<>();
    private ClickListenerWrapper<ClickListener> clickListenerWrapper = new ClickListenerWrapper<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AppLineViewHolder.build(parent, clickListenerWrapper);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppLineViewHolder) {
            ((AppLineViewHolder) holder).bind(getItems(position));
        }
    }

    @Override
    public AppsLinesAdapter onClick(ClickListener clickListener) {
        this.clickListenerWrapper.setListener(clickListener);
        return this;
    }


    public List<App> getItems(int line) {
        final List<App> apps = new ArrayList<>();
        for (int i = 0; i < NB_ITEMS_PER_LINE; i++) {
            final int index = (int) (line * NB_ITEMS_PER_LINE + i);
            if (index < appList.size()) {
                apps.add(appList.get(index));
            }
        }
        return apps;
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(appList.size() * 1f / NB_ITEMS_PER_LINE);
    }

    public void setItems(List<App> items) {
        this.appList.clear();
        this.appList.addAll(items);
        notifyDataSetChanged();
    }
}
