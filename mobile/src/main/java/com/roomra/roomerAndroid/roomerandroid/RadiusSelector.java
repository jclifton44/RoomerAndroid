package com.roomra.roomerAndroid.roomerandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class RadiusSelector extends ImageView implements View.OnTouchListener{
    public static View updater;
    public RadiusSelector(Context context) {
        this(context, null);
        setOnTouchListener(this);
   }

    public RadiusSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
            view.startDrag(null, dsb, view, 0);
            //view.setVisibility(View.INVISIBLE);
            return true;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            view.setVisibility(View.VISIBLE);
            return true;
        } else {
            return false;
        }
    }


}