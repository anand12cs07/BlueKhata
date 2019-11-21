package com.everyrupee.utils.recyclerviewdrager;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by aman on 04-09-2018.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
