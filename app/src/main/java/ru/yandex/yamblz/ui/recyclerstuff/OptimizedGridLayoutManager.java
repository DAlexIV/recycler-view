package ru.yandex.yamblz.ui.recyclerstuff;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by dalexiv on 8/5/16.
 */

public class OptimizedGridLayoutManager extends GridLayoutManager {
    public OptimizedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return 2 * super.getExtraLayoutSpace(state);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}
