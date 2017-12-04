package com.matpag.clickdrawabletextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Custom-Sizable drawable
 *
 * Created by Mattia Pagini on 12/02/2017.
 */
public class CsDrawable {

    /**
     * The wrapped drawable
     */
    private Drawable drawable;

    /**
     * The application context
     */
    private Context context;

    /**
     * The visibility flag for this {@link CsDrawable}
     */
    private boolean visibility;

    CsDrawable(Context context, @DrawableRes int drawableRes){
        this(context, ContextCompat.getDrawable(context, drawableRes));
    }

    CsDrawable(Context context, Drawable drawable){
        if (drawable == null){
            throw new IllegalArgumentException("drawable can't be null");
        }
        if (context == null){
            throw new IllegalArgumentException("context can't be null");
        }
        this.context = context.getApplicationContext();
        this.drawable = drawable;
        this.visibility = true;
        setDefaultDrawableIntrinsicBounds();
    }

    public boolean isVisible(){
        return visibility;
    }

    void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    public @NonNull Drawable getDrawable(){
        return drawable;
    }

    public @Nullable Drawable getDrawableIfVisible(){
        return visibility ? drawable : null;
    }

    private Context getContext(){
        return context;
    }

    /**
     * Validate the dimension params before sizing the drawable
     * @param height the requested height of the drawable
     * @param width the requested width of the drawable
     * @return true if params are correct, false otherwise
     */
    private static boolean validateSizeParams(int height, int width){
        if (width < 0 || height < 0){
            throw new IllegalArgumentException("CsDrawable requested height and width must be >= 0");
        }
        return true;
    }

    void setDrawablePixelSize(int height, int width){
        drawable.setBounds(0, 0, width, height);
    }

    void setDrawableDpSize(int heightDp, int widthDp){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int pixelWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDp, metrics);
        int pixelHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp, metrics);
        drawable.setBounds(0, 0, pixelWidth, pixelHeight);
    }

    private void setDefaultDrawableIntrinsicBounds(){
        drawable.setBounds(0, 0,
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    /**
     * The builder to create a {@link CsDrawable} object in the easy way
     */
    public final static class Builder {

        private CsDrawable csDrawable;

        public Builder(Context context, @NonNull Drawable drawable){
            csDrawable = new CsDrawable(context, drawable);
        }

        public Builder(Context context, @DrawableRes int drawableRes){
            csDrawable = new CsDrawable(context, drawableRes);
        }

        public Builder setDrawablePixelSize(int pixelHeight, int pixelWidth){
            if (validateSizeParams(pixelHeight, pixelWidth)) {
                csDrawable.setDrawablePixelSize(pixelHeight, pixelWidth);
            }
            return this;
        }

        public Builder setDrawableDpSize(int dpHeight, int dpWidth){
            if (validateSizeParams(dpHeight, dpWidth)) {
                csDrawable.setDrawableDpSize(dpHeight, dpWidth);
            }
            return this;
        }

        public Builder setVisibility(boolean visible){
            csDrawable.setVisibility(visible);
            return this;
        }

        /**
         * Set the tint for the current drawable
         * @param color the color to add to the drawable
         */
        public Builder setTint(@ColorRes int color){
            if (color == 0){
                throw new IllegalArgumentException("0 is not a valid color");
            }
            DrawableCompat.setTint(csDrawable.getDrawable(),
                    csDrawable.getContext().getResources().getColor(color));
            return this;
        }

        /**
         * Set the tint for the current drawable
         * @param tintList the colorStateList to add to the drawable
         */
        public Builder setTintList(@NonNull ColorStateList tintList){
            DrawableCompat.setTintList(csDrawable.getDrawable(), tintList);
            return this;
        }

        /**
         * Set the tint for the current drawable
         * @param mode the mode to use
         */
        public Builder setTintMode(@NonNull PorterDuff.Mode mode){
            DrawableCompat.setTintMode(csDrawable.getDrawable(), mode);
            return this;
        }

        public CsDrawable build(){
            return csDrawable;
        }

    }

}
