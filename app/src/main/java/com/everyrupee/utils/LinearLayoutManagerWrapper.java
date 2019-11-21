package com.everyrupee.utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

public class LinearLayoutManagerWrapper extends LinearLayoutManager {

    public LinearLayoutManagerWrapper(Context context){
        super(context);
    }

    public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout){
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch (Exception e){

        }
    }
}
