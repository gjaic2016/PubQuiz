package com.pubquiz.recycler;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class NoScrollLinearLayoutManager extends LinearLayoutManager {

    // overridean LinearLayoutManager da mozemo onemogucit scrollanje
    private boolean canScroll = false;

    public NoScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollHorizontally() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}
