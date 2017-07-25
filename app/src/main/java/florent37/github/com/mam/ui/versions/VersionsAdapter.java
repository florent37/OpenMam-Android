package florent37.github.com.mam.ui.versions;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import florent37.github.com.mam.common.ClickListenerWrapper;
import florent37.github.com.mam.model.AppVersion;

public class VersionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER_SIZE = 2;

    private final int TYPE_HEADER = 1;
    private final int TYPE_CELL_FIRST = 2;
    private final int TYPE_CELL = 3;

    private List<AppVersion> appList = new ArrayList<>();
    private ClickListenerWrapper<ClickListener> clickListenerWrapper = new ClickListenerWrapper<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_CELL_FIRST:
                return AppViewHolder.build(parent, clickListenerWrapper);
            case TYPE_CELL:
                return VersionsViewHolder.build(parent, clickListenerWrapper);
            case TYPE_HEADER:
                return VersionsHeaderViewHolder.build(parent, clickListenerWrapper);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        } else if(position == 1){
            return TYPE_CELL_FIRST;
        }
        return TYPE_CELL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_CELL) {
            if (holder instanceof VersionsViewHolder) {
                ((VersionsViewHolder) holder).bind(getItem(position));
            }
        }
    }

    public VersionsAdapter onClick(ClickListener clickListener){
        this.clickListenerWrapper.setListener(clickListener);
        return this;
    }


    private AppVersion getItem(int position) {
        return appList.get(position - HEADER_SIZE);
    }

    @Override
    public int getItemCount() {
        return appList.size() + HEADER_SIZE;
    }

    public void setItems(List<AppVersion> items) {
        this.appList.clear();
        this.appList.addAll(items);
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onAppClicked(AppVersion appVersion);
    }
}
