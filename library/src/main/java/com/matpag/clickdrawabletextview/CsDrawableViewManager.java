package com.matpag.clickdrawabletextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.matpag.ui.clickdrawable.R;
import com.matpag.clickdrawabletextview.interfaces.ClickableDrawable;
import com.matpag.clickdrawabletextview.interfaces.OnDrawableClickListener;

/**
 *
 * Non sarà assolutamente supportato il fatto che il width e l'height del campo varino a runtime,
 * perchè non so come possiamo fare sinceramente, forse bisogna collegarsi all'evento onSizeChanged
 * ma solo dopo aver passato la prima inizializzazione
 *
 * Created by Mattia Pagini on 23/04/2017.
 */

final class CsDrawableViewManager implements ClickableDrawable {

    private TextView view;

    private Context mContext;

    private CsDrawable mStartDrawable;
    private CsDrawable mTopDrawable;
    private CsDrawable mEndDrawable;
    private CsDrawable mBottomDrawable;

    private DrawablePosition mTouchedPosition;

    private static DisplayMetrics mMetrics;

    //default true
    private boolean enableTouchOnText = true;

    //default true
    private boolean enableRTL = true;

    private Configuration mConfig;

    /**
     * Max allowed duration for a "click", in milliseconds.
     */
    private static final int MAX_CLICK_DURATION = 1500;

    /**
     * Max allowed distance to move during a "click", in DP.
     */
    private static final int MAX_CLICK_DISTANCE = 15;

    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private boolean stayedWithinClickDistance;

    /**
     * Inteface to listen for drawable click
     */
    private OnDrawableClickListener mOnDrawableClickListener;

    CsDrawableViewManager(TextView view){
        this.view = view;
    }

    /**
     * Init method to call in every {@link TextView} subclass constructor
     * @param context the context
     * @param attrs the attributes passed from XML
     */
    void init(Context context, AttributeSet attrs) {
        mContext = context;

        mConfig = context.getResources().getConfiguration();

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CsDrawableViewManager);

            mMetrics = context.getResources().getDisplayMetrics();

            Drawable leftDrawable = a.getDrawable(
                    R.styleable.CsDrawableViewManager_csStartDrawable);
            if (leftDrawable != null) {
                mStartDrawable = new CsDrawable(context, leftDrawable);
                int height = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csStartDrawableHeight, -1);
                int width = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csStartDrawableWidth, -1);
                if (height > -1 && width > -1) {
                    mStartDrawable.setDrawablePixelSize(height, width);
                }
                boolean visibility = a.getBoolean(
                        R.styleable.CsDrawableViewManager_csStartDrawableVisible, true);
                mStartDrawable.setVisibility(visibility);
            }

            Drawable topDrawable = a.getDrawable(
                    R.styleable.CsDrawableViewManager_csTopDrawable);
            if (topDrawable != null) {
                mTopDrawable = new CsDrawable(context, topDrawable);
                int height = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csTopDrawableHeight, -1);
                int width = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csTopDrawableWidth, -1);
                if (height > -1 && width > -1) {
                    mTopDrawable.setDrawablePixelSize(height, width);
                }
                boolean visibility = a.getBoolean(
                        R.styleable.CsDrawableViewManager_csTopDrawableVisible, true);
                mTopDrawable.setVisibility(visibility);
            }

            Drawable rightDrawable = a.getDrawable(
                    R.styleable.CsDrawableViewManager_csEndDrawable);
            if (rightDrawable != null) {
                mEndDrawable = new CsDrawable(context, rightDrawable);
                int height = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csEndDrawableHeight, -1);
                int width = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csEndDrawableWidth, -1);
                if (height > -1 && width > -1) {
                    mEndDrawable.setDrawablePixelSize(height, width);
                }
                boolean visibility = a.getBoolean(
                        R.styleable.CsDrawableViewManager_csEndDrawableVisible, true);
                mEndDrawable.setVisibility(visibility);
            }

            Drawable bottomDrawable = a.getDrawable(
                    R.styleable.CsDrawableViewManager_csBottomDrawable);
            if (bottomDrawable != null) {
                mBottomDrawable = new CsDrawable(context, bottomDrawable);
                int height = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csBottomDrawableHeight, -1);
                int width = a.getDimensionPixelSize(
                        R.styleable.CsDrawableViewManager_csBottomDrawableWidth, -1);
                if (height > -1 && width > -1) {
                    mBottomDrawable.setDrawablePixelSize(height, width);
                }
                boolean visibility = a.getBoolean(
                        R.styleable.CsDrawableViewManager_csBottomDrawableVisible, true);
                mBottomDrawable.setVisibility(visibility);
            }

            enableRTL = a.getBoolean(R.styleable.CsDrawableViewManager_csEnableRTL,
                    true);

            a.recycle();
        }

        invalidateDrawables();

        setViewOnTouchListener();
    }

    /**
     * Check if the current Locale is in RTL mode and if the user has enabled the view to use it
     * with {@link #enableRTL(boolean)}
     *
     * NOTE: We should use <code>ViewCompat.getLayoutDirection(view) ==
     * ViewCompat.LAYOUT_DIRECTION_RTL</code> but when you use developer option : Force RTL Layout
     * this is not working correctly (read <a href="http://stackoverflow.com/a/18996319/2910520">
     * here</a>), checking the configuration is the only rieliable way
     * to get the current direction without adding an extra utility to check if the app is running
     * in the emulator
     *
     * @return true if in RTL, false otherwise
     */
    private boolean isLayoutRTL(){
        return mConfig.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL && enableRTL;
    }

    /**
     * Add the drawables to the view
     */
    private void invalidateDrawables(){
        if (isLayoutRTL()) {
            addCompoundDrawablesRelative();
        } else {
            addCompoundDrawables();
        }
    }

    /**
     * Add the compound drawables to the view, using the default method which
     * not take into account RTL support
     */
    private void addCompoundDrawables(){
        view.setCompoundDrawables(
                mStartDrawable != null ? mStartDrawable.getDrawableIfVisible() : null,
                mTopDrawable != null ? mTopDrawable.getDrawableIfVisible() : null,
                mEndDrawable != null ? mEndDrawable.getDrawableIfVisible() : null,
                mBottomDrawable != null ? mBottomDrawable.getDrawableIfVisible() : null
        );
    }

    /**
     * Add the compound drawables to the view, if the Locale of the user is in
     * RTL mode and {@link #enableRTL} is true the drawable will be added
     * in the proper position
     */
    private void addCompoundDrawablesRelative(){
        view.setCompoundDrawablesRelative(
                mStartDrawable != null ? mStartDrawable.getDrawableIfVisible() : null,
                mTopDrawable != null ? mTopDrawable.getDrawableIfVisible() : null,
                mEndDrawable != null ? mEndDrawable.getDrawableIfVisible() : null,
                mBottomDrawable != null ? mBottomDrawable.getDrawableIfVisible() : null
        );
    }

    /**
     * Handle all the touch events of the current {@link #view} object
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setViewOnTouchListener(){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //se ho cliccato uno dei drawable, allora me lo salvo
                        if (isOneOfDrawablesTouched(e)) {
                            pressStartTime = System.currentTimeMillis();
                            pressedX = e.getX();
                            pressedY = e.getY();
                            stayedWithinClickDistance = true;
                            return true;
                        } else if (!enableTouchOnText){
                            return true;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if (stayedWithinClickDistance && distance(pressedX, pressedY, e.getX(),
                                        e.getY()) > MAX_CLICK_DISTANCE) {
                            stayedWithinClickDistance = false;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        long pressDuration = System.currentTimeMillis() - pressStartTime;
                        if ((pressDuration < MAX_CLICK_DURATION) && stayedWithinClickDistance
                                && mTouchedPosition != null) {
                            dispatchDrawableClickEvent();
                            resetTouchedDrawable();
                            //dispatch accessibility events (even if the lint checker is still
                            //complaining about this
                            view.performClick();
                            return true;
                        }
                    }
                }
                resetTouchedDrawable();
                return false;
            }
        });
    }

    /**
     * Using the coordinates X and Y of the touch event, we check if they are inside
     * the bounds of one of the showing drawables, if true set {@link #mTouchedPosition}
     * with one of correct values of {@link DrawablePosition}
     */
    private boolean isOneOfDrawablesTouched(MotionEvent event){
        CsDrawableTouchUtils cdu = new CsDrawableTouchUtils(event, view, isLayoutRTL());
        if (mEndDrawable != null && mEndDrawable.isVisible()
                && cdu.isEndDrawableTouched(mEndDrawable)){
            mTouchedPosition = DrawablePosition.END;
        } else if (mStartDrawable != null && mStartDrawable.isVisible()
                && cdu.isStartDrawableTouched(mStartDrawable)){
            mTouchedPosition = DrawablePosition.START;
        } else if (mTopDrawable != null && mTopDrawable.isVisible()
                && cdu.isTopDrawableTouched(mTopDrawable)){
            mTouchedPosition = DrawablePosition.TOP;
        } else if (mBottomDrawable != null && mBottomDrawable.isVisible()
                && cdu.isBottomDrawableTouched(mBottomDrawable)){
            mTouchedPosition = DrawablePosition.BOTTOM;
        }
        return mTouchedPosition != null;
    }

    /**
     * Dispatch click event if the listener has been attached
     */
    private void dispatchDrawableClickEvent(){
        if (mOnDrawableClickListener != null && mTouchedPosition != null){
            mOnDrawableClickListener.onClick(view, mTouchedPosition);
        }
    }

    private void resetTouchedDrawable(){
        mTouchedPosition = null;
    }


    @Override
    public void setOnDrawableClickListener(OnDrawableClickListener listener){
        mOnDrawableClickListener = listener;
    }

    @Override
    public void removeOnDrawableClickListener(){
        mOnDrawableClickListener = null;
    }

    @Override
    public void addStartCsDrawable(CsDrawable csDrawable) {
        mStartDrawable = csDrawable;
        invalidateDrawables();
    }

    @Override
    public void addTopCsDrawable(CsDrawable csDrawable) {
        mTopDrawable = csDrawable;
        invalidateDrawables();
    }

    @Override
    public void addEndCsDrawable(CsDrawable csDrawable) {
        mEndDrawable = csDrawable;
        invalidateDrawables();
    }

    @Override
    public void addBottomCsDrawable(CsDrawable csDrawable) {
        mBottomDrawable = csDrawable;
        invalidateDrawables();
    }

    @Override
    public void showStartCsDrawable(boolean visible) {
        if (mStartDrawable.isVisible() != visible){
            mStartDrawable.setVisibility(visible);
            invalidateDrawables();
        }
    }

    @Override
    public void showTopCsDrawable(boolean visible) {
        if (mTopDrawable.isVisible() != visible) {
            mTopDrawable.setVisibility(visible);
            invalidateDrawables();
        }
    }

    @Override
    public void showEndCsDrawable(boolean visible) {
        if (mEndDrawable.isVisible() != visible) {
            mEndDrawable.setVisibility(visible);
            invalidateDrawables();
        }
    }

    @Override
    public void showBottomCsDrawable(boolean visible) {
        if (mBottomDrawable.isVisible() != visible) {
            mBottomDrawable.setVisibility(visible);
            invalidateDrawables();
        }
    }

    @Override
    public void enableRTL(boolean enable) {
        enableRTL = enable;
        invalidateDrawables();
    }

    @Override
    public void disableFocusOnText(boolean preventReFocus, boolean closeKeyboard) {
        //block refocus on others edittext
        if (preventReFocus) {
            ViewGroup rootView = (ViewGroup) view.getRootView();
            int dfValue = rootView.getDescendantFocusability();
            rootView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            view.clearFocus();
            rootView.setDescendantFocusability(dfValue);
        } else {
            view.clearFocus();
        }
        if (closeKeyboard){
            //hide the keyboard if opened
            setImeVisibility(false);
        }
        enableTouchOnText = false;
    }

    @Override
    public void enableFocusOnText(boolean openKeyboard) {
        enableTouchOnText = true;
        if (openKeyboard) {
            setImeVisibility(true);
            view.requestFocus();
        }
    }

    /**
     * Open the keyboard with some Google trick
     * <a href="http://stackoverflow.com/a/13306632/2910520">Link here</a>
     * @param visible true for open the keyboard, false to close it
     */
    private void setImeVisibility(final boolean visible) {
        if (visible) {
            view.post(mShowImeRunnable);
        } else {
            view.removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.showSoftInput(view, 0);
            }
        }
    };

    /**
     * Remove all the custom drawables
     */
    @Override
    public void removeAllCsDrawables() {
        mStartDrawable = null;
        mTopDrawable = null;
        mEndDrawable = null;
        mBottomDrawable = null;
        invalidateDrawables();
    }

    /**
     * Calculate the distance from 2 point in DP
     */
    private static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private static float pxToDp(float px) {
        return px / mMetrics.density;
    }

}
