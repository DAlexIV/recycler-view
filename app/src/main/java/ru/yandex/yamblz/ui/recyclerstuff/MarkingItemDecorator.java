package ru.yandex.yamblz.ui.recyclerstuff;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.yandex.yamblz.ui.recyclerstuff.interfaces.IGetItemColor;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static ru.yandex.yamblz.ui.recyclerstuff.HighlightingStates.WAS_DRAGGED;
import static ru.yandex.yamblz.ui.recyclerstuff.HighlightingStates.WAS_PREV_DRAGGED;

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

    public static void highlightView(View view, @HighlightingStates.States String state) {
        switch (state) {
            case WAS_DRAGGED:
                highlightViewWithColor(view, Color.RED);
                break;
            case WAS_PREV_DRAGGED:
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
        // Remove highlighting from previous elements if needed
        if (cachedDraggedPosition != NO_POSITION
                && cachedDraggedPosition != draggedPosition) {
            highlightItemView(parent, cachedDraggedPosition,
                    iGetItemColor.getDefaultColorForItem(cachedDraggedPosition));
        }
        if (cachedPrevDraggedPosition != NO_POSITION
                && cachedPrevDraggedPosition != prevDraggedPosition) {
            highlightItemView(parent, cachedPrevDraggedPosition,
                    iGetItemColor.getDefaultColorForItem(cachedPrevDraggedPosition));
        }

        // Highlight current elements if needed
        highlightItemView(parent, draggedPosition, WAS_DRAGGED);

        highlightItemView(parent, prevDraggedPosition, WAS_PREV_DRAGGED);
    }

    private void highlightItemView(RecyclerView parent, int position, String flag) {
        RecyclerView.ViewHolder vh
                = parent.findViewHolderForAdapterPosition(position);
        if (vh != null)
            highlightView(vh.itemView, flag);
    }
    private void highlightItemView(RecyclerView parent, int position, int color) {
        RecyclerView.ViewHolder vh
                = parent.findViewHolderForAdapterPosition(position);
        if (vh != null)
            highlightViewWithColor(vh.itemView, color);
    }

    public void updateHighlightedPositions(int draggedPosition, int lastDraggedPosition) {
        this.cachedDraggedPosition = this.draggedPosition;
        this.cachedPrevDraggedPosition = this.prevDraggedPosition;

        this.draggedPosition = draggedPosition;
        this.prevDraggedPosition = lastDraggedPosition;
    }
}

