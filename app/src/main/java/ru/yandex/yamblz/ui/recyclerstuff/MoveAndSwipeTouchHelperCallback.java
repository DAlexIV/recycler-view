package ru.yandex.yamblz.ui.recyclerstuff;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by dalexiv on 8/6/16.
 */

public class MoveAndSwipeTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    private ContentAdapter adapter;
    private IColorChanger iColorChanger;

    public MoveAndSwipeTouchHelperCallback(ContentAdapter adapter, IColorChanger iColorChanger) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.iColorChanger = iColorChanger;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        final int prevPos = viewHolder.getAdapterPosition();
        final int futurePos = target.getAdapterPosition();
        adapter.moveElement(prevPos, futurePos);
        adapter.notifyItemMoved(prevPos, futurePos);

        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deleteElement(viewHolder.getAdapterPosition());
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        // Mathematical logic FTW
        int currentRed = (((int) (255 * dX / viewHolder.itemView.getWidth())) << 24) | 0x00FF0000;
        iColorChanger.changeColor(currentRed);
    }
}