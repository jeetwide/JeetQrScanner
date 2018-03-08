package com.jituscanner;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by jeetendra.achtani on 08-03-2018.
 */

public class App extends Application
{
    public static Context mContext;
    @Override
    public void onCreate() {

        try {
            super.onCreate();

            mContext = getApplicationContext();

            MultiDex.install(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method converts dp unit to equivalent device specific value in pixels.
     *
     * @param dp      A value in dp(Device independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return An integer value to represent Pixels equivalent to dp according to device
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;
    }
}
