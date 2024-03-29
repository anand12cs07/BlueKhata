package com.bluekhata.utils.recyclerviewdrager;

/**
 * Created by aman on 04-09-2018.
 */

public interface ItemTouchHelperAdapter {
    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and not at the end of a "drop" event.
     *
     * @param fromPosition The start position of the moved item.
     * @param toPosition   Then end position of the moved item.

     */
    void onItemMove(int fromPosition, int toPosition);


    /**
     * Called when an item has been dismissed by a swipe.
     *
     * @param position The position of the item dismissed.

     */
    void onItemDismiss(int position);

    /**
     * Called when an item has been stop dragging
     * @param fromPosition
     * @param toPosition
     */
    void onItemMoveEnd(int fromPosition, int toPosition);
}
