package com.matpag.clickdrawabletextview.interfaces;

import android.view.View;

import com.matpag.clickdrawabletextview.DrawablePosition;

/**
 *
 * Interface for handling drawable touch events
 *
 * Created by Mattia Pagini on 03/03/2017.
 */
public interface OnDrawableClickListener {

    /**
     * Drawable click event
     * @param view one of the subclasses of the {@link android.widget.TextView} widget,
     *             which received the touch input
     * @param position the position of the clicked drawable
     */
    void onClick(View view, DrawablePosition position);

}
