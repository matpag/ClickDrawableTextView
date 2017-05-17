package com.matpag.sample;

import android.app.Application;

import com.matpag.clickdrawabletextview.CsDrawableSettings;

/**
 * Custom {@link Application} class
 *
 * Created by Mattia Pagini on 17/05/2017.
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Init the library
        CsDrawableSettings.init(this, BuildConfig.APPLICATION_ID);
    }
}
