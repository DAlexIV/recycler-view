package ru.yandex.yamblz.ui.recyclerstuff;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by dalexiv on 8/7/16.
 */

public class MarkingItemDecorator extends RecyclerView.ItemDecoration {
    private int draggedPosition = NO_POSITION;
    private int prevDraggedPosition = NO_POSITION;

    private int cachedDraggedPosition = NO_POSITION;
    private int cachedPrevDraggedPosition = NO_POSITION;
    private IGetItemColor iGetItemColor;

    public MarkingItemDecorator(IGetItemColor iGetItemColor) {
        this.iGetItemColor = iGetItemColor;
    }

    public static void highlightView(View view, String state) {
        switch (state) {
            case MoveAndSwipeTouchHelperCallback.WAS_DRAGGED_TAG:
                highlightViewWithColor(view, Color.RED);
                break;
            case MoveAndSwipeTouchHelperCallback.WAS_PREV_DRAGGED_TAG:
                highlightViewWithColor(view, Color.YELLOW);
                break;
        }
    }

    public static void highlightViewWithColor(View view, int color) {
        view.setBackgroundColor(color);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (cachedDraggedPosition != NO_POSITION && cachedDraggedPosition != draggedPosition) {
            highlightItemView(parent, cachedDraggedPosition,
                    iGetItemColor.getDefaultColorForItem(cachedDraggedPosition));
        }
        if (cachedPrevDraggedPosition != NO_POSITION
                && cachedPrevDraggedPosition != prevDraggedPosition) {
            highlightItemView(parent, cachedPrevDraggedPosition,
                    iGetItemColor.getDefaultColorForItem(cachedPrevDraggedPosition));
        }

        highlightItemView(parent, draggedPosition,
                MoveAndSwipeTouchHelperCallback.WAS_DRAGGED_TAG);

        highlightItemView(parent, prevDraggedPosition,
                MoveAndSwipeTouchHelperCallback.WAS_PREV_DRAGGED_TAG);
    }

    private void highlightItemView(RecyclerView parent, int position, String flag) {
        RecyclerView.ViewHolder vh
                = parent.findViewHolderForAdapterPosition(position);
        if (vh != null) {
            highlightView(vh.itemView, flag);
        }
    }
    private void highlightItemView(RecyclerView parent, int position, int color) {
        RecyclerView.ViewHolder vh
                = parent.findViewHolderForAdapterPosition(position);
        if (vh != null) {
            highlightViewWithColor(vh.itemView, color);
        }
    }

    public void setHighlightedPositions(int draggedPosition, int lastDraggedPosition) {
        // Update cache
        this.cachedDraggedPosition = this.draggedPosition;
        this.cachedPrevDraggedPosition = this.prevDraggedPosition;

        this.draggedPosition = draggedPosition;
        this.prevDraggedPosition = lastDraggedPosition;
    }
}

