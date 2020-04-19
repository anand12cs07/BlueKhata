package com.bluekhata.utils.recyclerviewdrager;

/**
 * Created by aman on 04-09-2018.
 */

public interface ItemTouchHelperViewHolder {
    /**
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * state should be cleared.
     */
    void onItemClear();
}
