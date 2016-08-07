package ru.yandex.yamblz.ui.recyclerstuff;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dalexiv on 8/7/16.
 */

public class HighlightingStates {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            WAS_DRAGGED,
            WAS_PREV_DRAGGED
    })
    public @interface States{}
    public static final String WAS_DRAGGED = "WAS_DRAGGED_TAG";
    public static final String WAS_PREV_DRAGGED = "WAS_PREV_DRAGGED_TAG";
}
