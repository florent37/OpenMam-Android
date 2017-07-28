package florent37.github.com.mam.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HeaderDecorator extends RecyclerView.ItemDecoration {

    public final int height;
    public final int headerCells;

    public HeaderDecorator(int headerCells, int height) {
        this.height = height;
        this.headerCells = headerCells;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);

        //RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //if (layoutManager instanceof GridLayoutManager) {
        //    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        //    headerCells = gridLayoutManager.getSpanCount();
        //}

        final int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < headerCells) {
            outRect.top = height;
        } else {
            outRect.top = 0;
        }
    }
}
