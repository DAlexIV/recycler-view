package ru.yandex.yamblz.ui.recyclerstuff;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by dalexiv on 8/5/16.
 */

public class OptimizedGridLayoutManager extends GridLayoutManager {
    public OptimizedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }


}
