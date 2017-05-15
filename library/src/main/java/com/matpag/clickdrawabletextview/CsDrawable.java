package com.matpag.clickdrawabletextview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Custom-Sizable drawable
 *
 * Created by Mattia Pagini on 12/02/2017.
 */
public class CsDrawable {

    private Drawable drawable;

    private Context context;

    private boolean visibility;

    CsDrawable(Context context, @DrawableRes int drawableRes){
        this(context, ContextCompat.getDrawable(context, drawableRes));
    }

    CsDrawable(Context context, @NonNull Drawable drawable){
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

        public CsDrawable build(){
            return csDrawable;
        }

    }

}
