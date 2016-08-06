package ru.yandex.yamblz.ui.recyclerstuff;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.yandex.yamblz.R;

/**
 * Created by dalexiv on 8/6/16.
 */
public class BorderItemDecorator extends RecyclerView.ItemDecoration {
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimaryDark));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        for (int i = 0; i < parent.getChildCount(); ++i) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            Rect viewBounds = new Rect(view.getLeft() + params.leftMargin,
                    view.getTop() + params.topMargin,
                    view.getRight() + params.rightMargin,
                    view.getBottom() + params.bottomMargin);

            int BOUND_WIDTH = view.getWidth() / 20;
            c.drawRect(viewBounds.left, viewBounds.top, viewBounds.right,
                    viewBounds.top + BOUND_WIDTH, paint);
            c.drawRect(viewBounds.left, viewBounds.top, viewBounds.left + BOUND_WIDTH,
                    viewBounds.bottom, paint);
            c.drawRect(viewBounds.right - BOUND_WIDTH, viewBounds.top, viewBounds.right,
                    viewBounds.bottom, paint);
            c.drawRect(viewBounds.left, viewBounds.bottom - BOUND_WIDTH,
                    viewBounds.right, viewBounds.bottom, paint);
        }
    }
}
