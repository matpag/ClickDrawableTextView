package com.matpag.clickdrawabletextview;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Class to handle touch event on the view and calculate if the touch are happening inside
 * the drawables we have defined
 *
 * Created by Mattia Pagini on 29/04/2017.
 */
final class CsDrawableTouchUtils {

    /**
     * <p>vSpace = vertical space available in the TextView</p>
     * <p>hSpace = horizontal space available in the TextView</p>
     */
    private int vSpace, hSpace = -1;

    /**
     * <p>hHeight = half height of the drawable bounds</p>
     * <p>hWidth = half width of the drawable bounds</p>
     */
    private int hHeight, hWidth = -1;

    /**
     * <p>centerY = the Y coordinates where the {@link TextView#onDraw(Canvas)} wants start to draw
     * the drawable from</p>
     * <p>centerX = the X coordinates where the {@link TextView#onDraw(Canvas)} wants start to draw
     * the drawable from the center</p>
     */
    private int centerY, centerX = -1;

    /**
     * The offset for the X and Y axis (if the view is inside a ScrollView or similar)
     */
    private int scrollX, scrollY;

    /**
     * The current touch event
     */
    private MotionEvent event;

    /**
     * The current touched view
     */
    private TextView view;

    /**
     * Support for RTL layout or not
     */
    private boolean isLayoutRTL;

    CsDrawableTouchUtils(MotionEvent event, TextView view, boolean isLayoutRTL){
        this.event = event;
        this.view = view;
        this.isLayoutRTL = isLayoutRTL;
        vSpace = view.getHeight() - view.getCompoundPaddingBottom() - view.getCompoundPaddingTop();
        hSpace = view.getWidth() - view.getCompoundPaddingRight() - view.getCompoundPaddingLeft();
        //if the drawable is extremely large (pushing the edges of the drawable itself
        //or of the other drawables out of the current view bounds, will not be possible
        //to calculate the correct touch position
        if (hSpace < 0 || vSpace < 0){
            throw new IllegalArgumentException("The size of one of your drawable is exceeding the" +
                    " calculated width or height of the view. In this case you should provide" +
                    "a smaller drawable or provide a smaller dimension in XML or with the builder");
        }
        scrollX = view.getScrollX();
        scrollY = view.getScrollY();
    }

    private boolean isClickInsideDrawableBounds(){
        return (event.getX() >= centerX - hWidth) && (event.getX() <= centerX + hWidth)
                && (event.getY() >= centerY - hHeight) && (event.getY() <= centerY + hHeight);
    }

    boolean isStartDrawableTouched(CsDrawable drawable){
        if (isLayoutRTL){
            return isRightDrawableTouched(drawable);
        } else {
            return isLeftDrawableTouched(drawable);
        }
    }

    boolean isTopDrawableTouched(CsDrawable drawable){
        hHeight = drawable.getDrawable().getBounds().height() / 2;
        hWidth = drawable.getDrawable().getBounds().width() / 2;
        centerX = scrollX + view.getCompoundPaddingLeft() + hSpace / 2;
        centerY = scrollY + view.getPaddingTop() + hHeight;
        return isClickInsideDrawableBounds();
    }

    boolean isEndDrawableTouched(CsDrawable drawable){
        if (isLayoutRTL){
            return isLeftDrawableTouched(drawable);
        } else {
            return isRightDrawableTouched(drawable);
        }
    }

    boolean isBottomDrawableTouched(CsDrawable drawable){
        hHeight = drawable.getDrawable().getBounds().height() / 2;
        hWidth = drawable.getDrawable().getBounds().width() / 2;
        centerX = scrollX + view.getCompoundPaddingLeft() + hSpace / 2;
        centerY = scrollY + view.getHeight() - view.getPaddingBottom() - hHeight;
        return isClickInsideDrawableBounds();
    }


    private boolean isLeftDrawableTouched(CsDrawable drawable){
        hHeight = drawable.getDrawable().getBounds().height() / 2;
        hWidth = drawable.getDrawable().getBounds().width() / 2;
        centerX = scrollX + view.getPaddingLeft() + hWidth;
        centerY = scrollY + view.getCompoundPaddingTop() + vSpace / 2;
        return isClickInsideDrawableBounds();
    }

    private boolean isRightDrawableTouched(CsDrawable drawable){
        hHeight = drawable.getDrawable().getBounds().height() / 2;
        hWidth = drawable.getDrawable().getBounds().width() / 2;
        centerX = scrollX + view.getWidth() - view.getPaddingRight() - hWidth;
        centerY = scrollY + view.getCompoundPaddingTop() + vSpace / 2;
        return isClickInsideDrawableBounds();
    }



}
