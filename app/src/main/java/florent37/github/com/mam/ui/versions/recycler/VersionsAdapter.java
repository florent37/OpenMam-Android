package florent37.github.com.mam.ui.versions.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import florent37.github.com.mam.common.ClickListenerWrapper;
import florent37.github.com.mam.model.App;
import florent37.github.com.mam.model.AppVersion;

public class VersionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_CELL_FIRST = 2;
    private final int TYPE_CELL = 3;

    private AppVersion headerObject;
    private App app;
    private List<AppVersion> list = new ArrayList<>();

    private ClickListenerWrapper<ClickListener> clickListenerWrapper = new ClickListenerWrapper<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CELL_FIRST:
                return AppViewHolder.build(parent, clickListenerWrapper);
            case TYPE_CELL:
                return VersionsViewHolder.build(parent, clickListenerWrapper);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_CELL_FIRST;
        }
        return TYPE_CELL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_CELL:
                if (holder instanceof VersionsViewHolder) {
                    ((VersionsViewHolder) holder).bind(getItem(position), app);
                }
                break;
            case TYPE_CELL_FIRST:
                if (holder instanceof AppViewHolder) {
                    ((AppViewHolder) holder).bind(headerObject);
                }
                break;
        }
    }

    public VersionsAdapter onClick(ClickListener clickListener) {
        this.clickListenerWrapper.setListener(clickListener);
        return this;
    }

    private AppVersion getItem(int position) {
        final int headerSize = headerObject != null ? 1 : 0;
        return list.get(position - headerSize);
    }

    @Override
    public int getItemCount() {
        final int headerSize = headerObject != null ? 1 : 0;
        return list.size() + headerSize;
    }

    public void setHeaderObject(App app, AppVersion item) {
        this.headerObject = item;
        this.app = app;
        notifyDataSetChanged();
    }

    public void setItems(List items) {
        this.list.clear();
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onAppClicked(AppVersion appVersion);
    }
}
