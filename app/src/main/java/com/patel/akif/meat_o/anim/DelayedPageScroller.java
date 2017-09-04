package com.patel.akif.meat_o.anim;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by akif_p on 24/05/2017.
 */

public class DelayedPageScroller extends Scroller {
    private static final int MIN = 0;
    private static final int MAX = 3000;
    private int mDuration = 0;

    public DelayedPageScroller(
            Context context,
            Interpolator interpolator,
            int duration) {
        super(context, interpolator);
        setCustomDuration(duration);
    }

    @Override
    public void startScroll(
            int startX,
            int startY,
            int dx,
            int dy,
            int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setCustomDuration(int customDuration) {
        if(customDuration <= MIN) {
            mDuration = MIN;
        }
        else if (customDuration >= MAX) {
            mDuration = MAX;
        }
        else {
            mDuration = customDuration;
        }
    }
}
