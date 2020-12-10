package com.tutorial.movieapp.ui.main.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener
{
    private final GestureDetector gestureDetector;
    private final OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public RecyclerItemClickListener(Context context, OnRecyclerViewItemClickListener listener)
    {
        this.onRecyclerViewItemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });
    }


    @Override

    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e)
    {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && onRecyclerViewItemClickListener != null && gestureDetector.onTouchEvent(e))
        {
            onRecyclerViewItemClickListener.onItemClicked(rv, childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e)
    {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {

    }

    public interface OnRecyclerViewItemClickListener
    {
        void onItemClicked(View parentView, View childView, int position);
    }
}
