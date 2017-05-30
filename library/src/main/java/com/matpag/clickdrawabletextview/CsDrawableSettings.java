package com.matpag.clickdrawabletextview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Global configuration to handle correctly some user specific choices
 *
 * Created by Mattia Pagini on 17/05/2017.
 */
public class CsDrawableSettings {

    private boolean rtlSupportEnabled;

    private static CsDrawableSettings mSettings;

    private CsDrawableSettings(boolean rtlSupportEnabled){
        this.rtlSupportEnabled = rtlSupportEnabled;
    }

    /**
     * Init method to call before every custom view initialization, this should preferably be
     * called in the custom {@link android.app.Application} app class. But you can use is in the
     * activity too before calling {@link android.app.Activity#setContentView(int)} or calling
     * this per Activity if you need to support RTL in some activities and not in others
     * @param context the app context
     * @param packageName the packageName of the app, <code>BuildConfig.APPLICATION_ID</code> should
     *                    be the proper choice in the most cases
     */
    public static void init(Context context, String packageName){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            PackageManager pManager = context.getApplicationContext().getPackageManager();
            try {
                ApplicationInfo appInfo = pManager.getApplicationInfo(packageName, 0);
                //read the Application android:supportsRtl xml properties (if present), default false
                boolean rtlSupport = (appInfo.flags & ApplicationInfo.FLAG_SUPPORTS_RTL) != 0;
                mSettings = new CsDrawableSettings(rtlSupport);
            } catch (PackageManager.NameNotFoundException nfe){
                throw new IllegalArgumentException("Unable to get info for the provided " +
                        "packageName, are you sure is it correct? BuildConfig.APPLICATION_ID " +
                        "should be fine in most cases");
            }
        } else {
            //prior to API 17 RTL is not supported, so we create settings instance
            //with default false support for RTL
            mSettings = new CsDrawableSettings(false);
        }
    }

    /**
     * Expose the RTL support flag
     * @return true if the developer added <code>android:supportsRtl="true"</code> to the
     * application manifest, false otherwise
     */
    static boolean isRtlSupportEnabled(){
        if (mSettings == null){
            throw new NullPointerException("You need to call CsDrawableSettings.init() in your " +
                    "custom Application class or Activity before using the library");
        }
        return mSettings.rtlSupportEnabled;
    }

}
