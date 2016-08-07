package ru.yandex.yamblz.ui.recyclerstuff;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

/**
 * Created by dalexiv on 8/6/16.
 */

public class MoveAndSwipeTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    public static final String WAS_DRAGGED_TAG = "WAS_DRAGGED_TAG";
    public static final String WAS_PREV_DRAGGED_TAG = "WAS_PREV_DRAGGED_TAG";

    private ContentAdapter adapter;
    private IColorChanger iColorChanger;
    private IMoveCallback iMoveCallback;

    public MoveAndSwipeTouchHelperCallback(ContentAdapter adapter,
                                           IColorChanger iColorChanger,
                                           IMoveCallback iMoveCallback) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.iColorChanger = iColorChanger;
        this.iMoveCallback = iMoveCallback;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        final int prevPos = viewHolder.getLayoutPosition();
        final int futurePos = target.getLayoutPosition();
        adapter.moveElement(prevPos, futurePos);
        iMoveCallback.moveHappened(futurePos, prevPos);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deleteElement(viewHolder.getAdapterPosition());
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        iColorChanger.changeColor(Color.WHITE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (isCurrentlyActive) {
            if (actionState == ACTION_STATE_SWIPE) {
                int redAlpha = (int) (255 * dX / recyclerView.getWidth());
                if (redAlpha > 255)
                    redAlpha = 255;

                int currentRed = (redAlpha << 24) | 0x00FF0000;
                iColorChanger.changeColor(currentRed);
            }
            else
                if (actionState == ACTION_STATE_DRAG) {
                    MarkingItemDecorator.highlightView(viewHolder.itemView, WAS_DRAGGED_TAG);
                }
        }
    }
}