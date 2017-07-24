package com.phonesj.news.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DefaultItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private OnItemTouchCallbackListener onItemTouchCallbackListener;

    private boolean isCanDrag = true;
    private boolean isCanSwipe = true;

    public DefaultItemTouchHelperCallback(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
    }

    public void setCanSwipe(boolean canSwipe) {
        isCanSwipe = canSwipe;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isCanDrag;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwipe;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = 0;
            return makeMovementFlags(dragFlag, swipeFlag);
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            int dragFlag = 0;
            int swipeFlag = 0;
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            } else if ((orientation == LinearLayoutManager.VERTICAL)) {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            if (orientation == LinearLayoutManager.HORIZONTAL) {// 如果是横向的布局
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else if (orientation == LinearLayoutManager.VERTICAL) {// 如果是竖向的布局，相当于ListView
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag, swipeFlag);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (onItemTouchCallbackListener != null) {
            return onItemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (onItemTouchCallbackListener != null) {
            onItemTouchCallbackListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    public interface OnItemTouchCallbackListener {
        boolean onMove(int srcPosition, int targetPosition);

        void onSwiped(int position);
    }
}
