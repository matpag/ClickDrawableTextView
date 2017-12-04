package com.matpag.clickdrawabletextview.interfaces;

import android.support.annotation.ColorRes;

import com.matpag.clickdrawabletextview.CsDrawable;

/**
 * The interface that every {@link android.widget.TextView} subclass need to implement to add
 * the extra functionalities to the attached drawables
 *
 * Created by Mattia Pagini on 24/04/2017.
 */
public interface ClickableDrawable {

    /**
     * Setup the listener to listen for the click events on the drawables
     * @param listener the listener
     */
    void setOnDrawableClickListener(OnDrawableClickListener listener);

    /**
     * Remove the listener for the drawable, short method that equals to call
     * {@link #setOnDrawableClickListener(OnDrawableClickListener)} with a <code>null</code>
     * parameter
     */
    void removeOnDrawableClickListener();

    /**
     * Add the start drawable to the view, default the LEFT drawable.
     * this could be the LEFT or RIGHT drawable based on the user locale if the developer
     * has added <code>android:supportsRtl="true"</code> in his <code>AndroidManifest.xml</code>
     * @param csDrawable the CsDrawable object (can be null)
     */
    void addStartCsDrawable(CsDrawable csDrawable);

    /**
     * Add the TOP drawable to the view
     * @param csDrawable the CsDrawable object (can be null)
     */
    void addTopCsDrawable(CsDrawable csDrawable);

    /**
     * Add the end drawable to the view, default the RIGHT drawable.
     * this could be the LEFT or RIGHT drawable based on the user locale if the developer
     * has added <code>android:supportsRtl="true"</code> in his <code>AndroidManifest.xml</code>
     * @param csDrawable the CsDrawable object (can be null)
     */
    void addEndCsDrawable(CsDrawable csDrawable);

    /**
     * Add the BOTTOM drawable to the view
     * @param csDrawable the CsDrawable object (can be null)
     */
    void addBottomCsDrawable(CsDrawable csDrawable);

    /**
     * Change the {@link CsDrawable} object visibility attached to the START position
     * @param visible true for show it in the view, false to hide
     */
    void showStartCsDrawable(boolean visible);

    /**
     * Change the {@link CsDrawable} object visibility attached to the TOP position
     * @param visible true for show it in the view, false to hide
     */
    void showTopCsDrawable(boolean visible);

    /**
     * Change the {@link CsDrawable} object visibility attached to the END position
     * @param visible true for show it in the view, false to hide
     */
    void showEndCsDrawable(boolean visible);

    /**
     * Change the {@link CsDrawable} object visibility attached to the BOTTOM position
     * @param visible true for show it in the view, false to hide
     */
    void showBottomCsDrawable(boolean visible);

    /**
     * Remove all the {@link CsDrawable} objects to the view
     */
    void removeAllCsDrawables();

    /**
     * Disable focus on the view, simulating a {@link android.view.View#setEnabled(boolean)}
     * call with a <code>false</code> parameter. In this way we can still handle the touch
     * inputs on the {@link CsDrawable}/s attached.
     * @param preventReFocus <code>true</code> if the focus leaving the current view should
     *                       not fall on another view inside the parent view. <code>false</code>
     *                       for default behaviour.
     * @param closeKeyboard <code>true</code> if the keyboard should be closed if opened when
     *                      this method is called
     */
    void disableFocusOnText(boolean preventReFocus, boolean closeKeyboard);

    /**
     * Re-enable the focus on the view, canceling a previous call to
     * {@link #disableFocusOnText(boolean, boolean)} method.
     * @param openKeyboard <code>true</code> if the keyboard should be opened if closed when this
     *                     method is called
     */
    void enableFocusOnText(boolean openKeyboard);

    /**
     * Helper method to close the keyboard if the IME is currently opened
     */
    void closeKeyboard();

    /**
     * Helper method to open the keyboard on the current view if the IME is currently closed
     */
    void openKeyboard();

}
